//2715375
package android.cs.stir.ac.uk.yhproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperTransaction(context: Context): SQLiteOpenHelper(context, "TransactionDatabase", null, 1) {

    //COMPANION OBJECT CONTAINING TABLE DETAILS
    companion object {
        private val TABLE_NAME = "TransactionTable"
        private val COL_ID = "id"
        private val COL_DATE = "transactionDate"
        private val COL_FROM_TEXT = "fromText"
        private val COL_FROM_PRICE = "fromPrice"
        private val COL_FROM_QUANTITY = "fromQuantity"
        private val COL_TO_TEXT = "toText"
        private val COL_TO_PRICE = "toPrice"
        private val COL_TO_QUANTITY = "toQuantity"
}

    //CREATES THE TABLE THAT STORES TRANSACTIONS
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =  ("CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY NOT NULL,$COL_DATE TEXT,$COL_FROM_TEXT TEXT,$COL_FROM_PRICE FLOAT,$COL_FROM_QUANTITY FLOAT,$COL_TO_TEXT TEXT,$COL_TO_PRICE FLOAT,$COL_TO_QUANTITY FLOAT);")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    //DESTROYS AND RECREATES THE TABLE THAT STORES TRANSACTIONS
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allTransactions:List<Transaction>
    //GETS AND RETURNS ALL TRANSACTIONS IN THE TABLE
        get() {
            val lstTransactions = ArrayList<Transaction>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    //GETS TRANSACTION CURSOR IS AT
                    val transaction = Transaction()
                    transaction.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    transaction.date = cursor.getString(cursor.getColumnIndex(COL_DATE))

                    transaction.fromTicker = cursor.getString(cursor.getColumnIndex(COL_FROM_TEXT))
                    transaction.fromPrice = cursor.getFloat(cursor.getColumnIndex(COL_FROM_PRICE))
                    transaction.fromQuantity = cursor.getFloat(cursor.getColumnIndex(COL_FROM_QUANTITY))

                    transaction.toTicker = cursor.getString(cursor.getColumnIndex(COL_TO_TEXT))
                    transaction.toPrice = cursor.getFloat(cursor.getColumnIndex(COL_TO_PRICE))
                    transaction.toQuantity = cursor.getFloat(cursor.getColumnIndex(COL_TO_QUANTITY))
                    lstTransactions.add(transaction)
                } while (cursor.moveToNext())
                //LOOPS UNTIL THERE ARE NO MORE ENTRIES IN QUERY

            }
            //RETURNS ALL THE TRANSACTIONS
            return lstTransactions
        }

    //ADDS THE ARGUMENT TRANSACTION TO THE TABLE
    fun addTransaction(transaction: Transaction) {
        val db = writableDatabase
        val values = ContentValues()

        //PUTS CONTENT INTO THE VALUES VARIABLE TO ALLOW SQLITE TO HANDLE THE DATA
        values.put(COL_DATE, transaction.date)

        values.put(COL_FROM_TEXT, transaction.fromTicker)
        values.put(COL_FROM_PRICE, transaction.fromPrice)
        values.put(COL_FROM_QUANTITY, transaction.fromQuantity)

        values.put(COL_TO_TEXT, transaction.toTicker)
        values.put(COL_TO_PRICE, transaction.toPrice)
        values.put(COL_TO_QUANTITY, transaction.toQuantity)

        //INSERTS TRANSACTION STORED IN VALUES INTO THE TABLE
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //UPDATES THE TRANSACTION WITH AN ID MATCHING THE ARGUMENT T0 THE ARGUMENT TRANSACTION
    fun updateTransaction(transaction: Transaction, id:Int):Int {
        val db = writableDatabase
        val values = ContentValues()

        //PUTS CONTENT INTO THE VALUES VARIABLE TO ALLOW SQLITE TO HANDLE THE DATA
        values.put(COL_DATE, transaction.date)

        values.put(COL_FROM_TEXT, transaction.fromTicker)
        values.put(COL_FROM_PRICE, transaction.fromPrice)
        values.put(COL_FROM_QUANTITY, transaction.fromQuantity)

        values.put(COL_TO_TEXT, transaction.toTicker)
        values.put(COL_TO_PRICE, transaction.toPrice)
        values.put(COL_TO_QUANTITY, transaction.toQuantity)

        //UPDATES THE TRANSACTION WITH THE REQUIRED ID
        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(id.toString()))
    }

    //DELETES THE TRANSACTION WITH AN ID MATCHING THE ARGUMENT FROM THE TABLE
    fun deleteTransaction(id:Int) {
        val db = this.writableDatabase

        //DELETES THE TRANSACTION WITH THE REQUIRED ID
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))

        //AFTER TRANSACTION IS DELETED, ALL LATER TRANSACTION IDS MUST BE DECREMENTED BY ONE TO ENSURE THERE ARE NO GAPS IN THE IDS
        db.execSQL("UPDATE $TABLE_NAME SET $COL_ID  = ( $COL_ID - 1) WHERE $COL_ID > " + id)
        db.close()
    }
}