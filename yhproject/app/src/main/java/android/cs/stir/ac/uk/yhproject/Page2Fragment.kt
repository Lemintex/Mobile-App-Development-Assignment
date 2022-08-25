//2715375
package android.cs.stir.ac.uk.yhproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class Page2Fragment : Fragment() {
    lateinit var model: SharedViewModel

    //THESE EDIT TEXT FIELDS ARE GLOBAL TO AVOID HAVING TO PASS LOTS OF ARGUMENTS TO VALIDATE/CLEAR METHODS
    lateinit var edtID: EditText

    lateinit var edtFromTicker: EditText
    lateinit var edtFromPrice: EditText
    lateinit var edtFromQuantity: EditText

    lateinit var edtToTicker: EditText
    lateinit var edtToPrice: EditText
    lateinit var edtToQuantity: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.page2_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //CREATES MODEL AND INITIALISES DATABASE HELPER
        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        model.initialiseDB(requireContext())

        //GETS ALL INPUT VIEWS IN UI AND STORES FOR EASY USE
        var txtDate = view.findViewById<TextView>(R.id.txtDate)

        edtID = view.findViewById(R.id.edtID)

        edtFromTicker = view.findViewById(R.id.editFromTicker)
        edtFromPrice = view.findViewById(R.id.editFromPrice)
        edtFromQuantity = view.findViewById(R.id.editFromQuantity)

        edtToTicker = view.findViewById(R.id.editToTicker)
        edtToPrice = view.findViewById(R.id.editToPrice)
        edtToQuantity = view.findViewById(R.id.editToQuantity)

        //GETS ALL BUTTONS IN UI AND STORES FOR EASY USE
        var btnAdd = view.findViewById<Button>(R.id.btnAdd)
        var btnClear = view.findViewById<Button>(R.id.btnClear)
        var btnEdit = view.findViewById<Button>(R.id.btnEdit)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        //SETS CLICK LISTENER FOR ADD BUTTON
        btnAdd.setOnClickListener {

            //ONLY PERFORMS ACTION IF ALL INPUTS ARE VALID
            if (validateInputs(true)) {
                var date = "" + model.day.value + "/" + model.month.value + "/" + model.year.value

                //CREATES TRANSACTION
                val transaction = Transaction(
                    date,
                    edtFromTicker.text.toString(),
                    edtFromPrice.text.toString().toFloat(),
                    edtFromQuantity.text.toString().toFloat(),
                    edtToTicker.text.toString(),
                    edtToPrice.text.toString().toFloat(),
                    edtToQuantity.text.toString().toFloat()
                )

                //ADDS THE TRANSACTION TO THE TABLE, EMPTIES FIELDS, AND REFRESHES THE TRANSACTION LIST
                model.db.addTransaction(transaction)
                emptyFields()
                model.refreshData()

                //NOTIFIES THE USER AN ADD OCCURRED
                Toast.makeText(context, "Transaction added", Toast.LENGTH_SHORT).show()
            }
        }

        //SETS CLICK LISTENER FOR CLEAR BUTTON
        btnClear.setOnClickListener{

            //CLEARS USER INPUTS
            emptyFields()
        }

        //SETS CLICK LISTENER FOR EDIT BUTTON
        btnEdit.setOnClickListener {

            //ONLY PERFORMS ACTION IF ALL INPUTS ARE VALID
            if (validateInputs(true) && validateInputs(false)) {
                var date = "" + model.day.value + "/" + model.month.value + "/" + model.year.value
                var id = edtID.text.toString().toInt()

                //CREATES TRANSACTION
                val transaction = Transaction(
                    date,
                    edtFromTicker.text.toString(),
                    edtFromPrice.text.toString().toFloat(),
                    edtFromQuantity.text.toString().toFloat(),
                    edtToTicker.text.toString(),
                    edtToPrice.text.toString().toFloat(),
                    edtToQuantity.text.toString().toFloat()
                )

                //UPDATES TRANSACTION WITH MATCHING ID, EMPTIES FIELDS, AND UPDATES TRANSACTION LIST
                model.db.updateTransaction(transaction, id)
                emptyFields()
                model.refreshData()

                //NOTIFIES THE USER AN EDIT OCCURRED
                Toast.makeText(context, "Transaction " + id + " edit attemptes" , Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {

            //ONLY PERFORMS ACTION IF ALL INPUTS ARE VALID
            if (validateInputs(false)) {
                var id = edtID.text.toString().toInt()

                //DELETES TRANSACTION, EMPTIES FIELDS, AND REFRESHES TRANSACTION LIST
                model.db.deleteTransaction(id)
                emptyFields()
                model.refreshData()

                //NOTIFIES THE USER A DELETE OCCURRED
                Toast.makeText(context, "Attempted to delete transaction " + id, Toast.LENGTH_SHORT).show()
            }
        }

        //CREATES THE OBSERVERS ON THE MODEL DATE VARIABLES, UPDATING THE DATE IF IT CHANGES
            model.day.observe(viewLifecycleOwner, Observer {
                txtDate.setText("" + model.day.value + "/" + model.month.value + "/" + model.year.value)
            })
            model.month.observe(viewLifecycleOwner, Observer {
                txtDate.setText("" + model.day.value + "/" + model.month.value + "/" + model.year.value)
            })
            model.year.observe(viewLifecycleOwner, Observer {
                txtDate.setText("" + model.day.value + "/" + model.month.value + "/" + model.year.value)
            })

        //INITIALISES THE DATE AND DATE DISPLAY
        txtDate.setText("" + model.day.value + "/" + model.month.value + "/" + model.year.value)

        //THIS MESS OF OBSERVERS UPDATES THE EDIT TEXT FIELDS WHENEVER AN ITEM IS CLICKED ON THE TRANSACTION LIST
        model.id.observe(viewLifecycleOwner, {
            edtID.setText(model.id.value.toString())
        })
        model.fromTicker.observe(viewLifecycleOwner, {
            edtFromTicker.setText(model.fromTicker.value)
        })
        model.fromPrice.observe(viewLifecycleOwner, {
            edtFromPrice.setText(model.fromPrice.value.toString())
        })
        model.fromQuantity.observe(viewLifecycleOwner, {
            edtFromQuantity.setText(model.fromQuantity.value.toString())
        })
        model.toTicker.observe(viewLifecycleOwner, {
            edtToTicker.setText(model.toTicker.value)
        })
        model.toPrice.observe(viewLifecycleOwner, {
            edtToPrice.setText(model.toPrice.value.toString())
        })
        model.toQuantity.observe(viewLifecycleOwner, {
            edtToQuantity.setText(model.toQuantity.value.toString())
        })
    }

    //VALIDATES ALL APPROPRIATE INPUTS, RETURNS A BOOLEAN WHICH REPRESENTS IF THE INPUT WAS VALID
    fun validateInputs(validateEntry: Boolean): Boolean
    {
        if (validateEntry) {
            var fromTicker = edtFromTicker.text.toString()
            var fromPrice = edtFromPrice.text.toString()
            var fromQuantity = edtFromQuantity.text.toString()

            var toTicker = edtToTicker.text.toString()
            var toPrice = edtToPrice.text.toString()
            var toQuantity = edtToQuantity.text.toString()

            //ENSURE FROM TICKER IS NOT EMPTY
            if (fromTicker.length == 0) {
                edtFromTicker.setError("FIELD CANNOT BE EMPTY")
                return false
            }
            //ENSURE FROM PRICE IS NOT EMPTY
            if (fromPrice.length == 0) {
                edtFromPrice.setError("FIELD CANNOT BE EMPTY")
                return false
            }
            //ENSURE FROM PRICE IS NUMERIC
            if (fromPrice.matches("[a-zA-Z ]+".toRegex())) {
                edtFromPrice.setError("FIELD MUST CONTAIN A FLOAT")
                return false
            }
            //ENSURE FROM QUANTITY IS NOT EMPTY
            if (fromQuantity.length == 0) {
                edtFromQuantity.setError("FIELD CANNOT BE EMPTY")
                return false
            }
            //ENSURE FROM QUANTITY IS NUMERIC
            if (fromQuantity.matches("[a-zA-Z]".toRegex())) {
                edtFromQuantity.setError("FIELD MUST CONTAIN A FLOAT")
                return false
            }

            //ENSURE TO TICKER IS NOT EMPTY
            if (toTicker.length == 0) {
                edtToTicker.setError("FIELD CANNOT BE EMPTY")
                return false
            }
            //ENSURE TO PRICE IS NOT EMPTY
            if (toPrice.length == 0) {
                edtToPrice.setError("FIELD CANNOT BE EMPTY")
                return false
            }
            //ENSURE TO PRICE IS NUMERIC
            if (toPrice.matches("[a-zA-Z ]+".toRegex())) {
                edtToPrice.setError("FIELD MUST CONTAIN A FLOAT")
                return false
            }
            //ENSURE TO QUANTITY IS NOT EMPTY
            if (toQuantity.length == 0) {
                edtToQuantity.setError("FIELD CANNOT BE EMPTY")
                return false
            }
            //ENSURE TO QUANTITY IS NUMERIC
            if (toQuantity.matches("[a-zA-Z ]+".toRegex())) {
                edtToQuantity.setError("FIELD MUST CONTAIN A FLOAT")
                return false
            }
            return true
        }
        else{
            var id = edtID.text.toString()
                //ENSURE ID IS NOT EMPTY
            if (id.length == 0){
                edtID.setError("FIEL CANNOT BE EMPTY")
                return false
            }
            //ENSURE ID IS NUMBERIC
            if (!id.matches("[0-9]".toRegex())){
                edtID.setError("FIELD MUST CONTAIN AN INTEGER")
                return false
            }
            return true
        }
    }

    //CLEARS ALL USER INPUTS
    fun emptyFields() {
        edtID.setText("")

        edtFromTicker.setText("")
        edtFromPrice.setText("")
        edtFromQuantity.setText("")

        edtToTicker.setText("")
        edtToPrice.setText("")
        edtToQuantity.setText("")
    }
}