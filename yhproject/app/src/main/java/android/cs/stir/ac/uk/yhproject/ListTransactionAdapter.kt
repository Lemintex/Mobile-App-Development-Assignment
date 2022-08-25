//2715375
package android.cs.stir.ac.uk.yhproject

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
class ListTransactionAdapter(internal var activity: Activity,
                             internal var lstTransaction: List<Transaction>,
                             internal var model: SharedViewModel):BaseAdapter() {

    private var inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //RETURNS NUMBER OF TRANSACTIONS STORED
    override fun getCount(): Int {
        return lstTransaction.size
    }

    //RETURNS TRANSACTION AT POSITION
    override fun getItem(position: Int): Any {
        return lstTransaction[position]
    }

    //RETURNS TRANSACTION ID AT POSITION
    override fun getItemId(position: Int): Long {
        return lstTransaction[position].id.toLong()
    }

    //RETURNS VIEW FOR TRANSACTION AT POSITION
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //CREATES MODEL
        this.model = model

        //STORE THE UI ELEMENTS TO SET THEIR TEXT TO THE TRANSACTION DATA
        var rowView = inflater.inflate(R.layout.transaction_layout,null)
        var txtID = rowView.findViewById<TextView>(R.id.txtID)
        var txtDate = rowView.findViewById<TextView>(R.id.txtDate)

        var txtFromTicker = rowView.findViewById<TextView>(R.id.txtFromTicker)
        var txtFromPrice = rowView.findViewById<TextView>(R.id.txtFromPrice)
        var txtFromQuantity = rowView.findViewById<TextView>(R.id.txtFromQuantity)

        var txtToTicker = rowView.findViewById<TextView>(R.id.txtToTicker)
        var txtToPrice = rowView.findViewById<TextView>(R.id.txtToPrice)
        var txtToQuantity = rowView.findViewById<TextView>(R.id.txtToQuantity)

        //FILLS THE TEXT FIELDS WITH THE TRANSACTION DATA
        txtID.setText(lstTransaction[position].id.toString())
        txtDate.setText(lstTransaction[position].date.toString())

        txtFromTicker.setText(lstTransaction[position].fromTicker.toString())
        txtFromPrice.setText("£" + String.format("%.2f",lstTransaction[position].fromPrice))
        txtFromQuantity.setText(lstTransaction[position].fromQuantity.toString())

        txtToTicker.setText(lstTransaction[position].toTicker.toString())
        txtToPrice.setText("£" + String.format("%.2f",lstTransaction[position].toPrice))
        txtToQuantity.setText(lstTransaction[position].toQuantity.toString())

        //IF CLICKED, UPDATE THE MUTABLE LIVE DATA IN THE MODEL
        rowView.setOnClickListener{
            model.id.value = lstTransaction[position].id
            model.fromTicker.value = lstTransaction[position].fromTicker
            model.fromPrice.value = lstTransaction[position].fromPrice
            model.fromQuantity.value = lstTransaction[position].fromQuantity
            model.toTicker.value = lstTransaction[position].toTicker
            model.toPrice.value = lstTransaction[position].toPrice
            model.toQuantity.value = lstTransaction[position].toQuantity
        }

        //RETURN THE VIEW
        return rowView
    }
}