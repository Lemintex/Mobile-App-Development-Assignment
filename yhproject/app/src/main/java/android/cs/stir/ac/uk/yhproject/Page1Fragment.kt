//2715375
package android.cs.stir.ac.uk.yhproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class Page1Fragment : Fragment() {
    lateinit var model: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.page1_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val cView = view.findViewById<View>(R.id.calendarView) as CalendarView

        //INITIALISES DATE
        model.initialiseDate()

        //LISTENER UPDATES THE DATE WHEN CHANGED
        cView.setOnDateChangeListener { _, year, month, day ->
            model.day.value = day
            model.month.value = month + 1//MONTH RETURNS 0-11 SO INCREMENTED BY 1 FOR VALID DATE
            model.year.value = year
        }
    }
}