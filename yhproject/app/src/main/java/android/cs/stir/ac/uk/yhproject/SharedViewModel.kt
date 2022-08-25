//2715375
package android.cs.stir.ac.uk.yhproject

import android.app.Activity
import android.content.Context
import android.widget.ListView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel : ViewModel() {

    lateinit var db:DBHelperTransaction

    internal var lstTransactions:List<Transaction> = ArrayList<Transaction>()

    lateinit var listAllTransactions:ListView
    lateinit var listActivity: Activity

    //MUTABLE LIVE DATA INT VARIABLES TO STORE DATE INFO
    var day = MutableLiveData<Int>()
    var month = MutableLiveData<Int>()
    var year = MutableLiveData<Int>()

    //MUTABLE LIVE DATA VARIABLES TO STORE TRANSACTION INFO
    var id = MutableLiveData<Int>()
    var fromTicker = MutableLiveData<String>()
    var fromPrice = MutableLiveData<Float>()
    var fromQuantity = MutableLiveData<Float>()

    var toTicker = MutableLiveData<String>()
    var toPrice = MutableLiveData<Float>()
    var toQuantity = MutableLiveData<Float>()

    //FLAG TO ENSURE THE DATE ISN'T INITIALISED MULTIPLE TIMES
    var dateInitialised = false

    //INITIALISES THE DATE
    fun initialiseDate() {

        //ENSURES DATE IS NOT INITIALISED MORE THAN ONCE
        if (!dateInitialised) {
            day.value = 1
            month.value = 1
            year.value = 1970

            //SET FLAG TO TRUE TO ENSURE THE DATE IS NEVER INITIALISED AGAIN
            dateInitialised = true
        }
    }

    //INITIALISES THE DATABASE
    fun initialiseDB(context:Context) {
        db = DBHelperTransaction(context)
    }

    //REFRESHES THE DATA DISPLAYED IN THE THIRD PAGE
    fun refreshData() {
        lstTransactions = db.allTransactions
        var adapter = ListTransactionAdapter(listActivity, lstTransactions, this)
        listAllTransactions.adapter = adapter
    }
}
