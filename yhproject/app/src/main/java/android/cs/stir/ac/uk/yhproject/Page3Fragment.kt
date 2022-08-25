//2715375
package android.cs.stir.ac.uk.yhproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class Page3Fragment : Fragment() {
    lateinit var model: SharedViewModel
    lateinit var inflater: LayoutInflater

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        return inflater.inflate(R.layout.page3_fragment, container, false)
    }

    //CREATES AND REFRESHES THE LIST OF TRANSACTIONS
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        model.listActivity = this.requireActivity()
        model.listAllTransactions = view.findViewById(R.id.listAllTransactions)

        //REFRESHES ON CREATION IN CASE USER TRIES TO VIEW LIST WITHOUT EDITING IT
        model.refreshData()
    }
}