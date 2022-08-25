//2715375
package android.cs.stir.ac.uk.yhproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPageAdapter(fa: FragmentActivity, private val numOfTabs: Int) :
    FragmentStateAdapter(fa) {

    //RETURNS THE NUMBER OF TABS
    override fun getItemCount(): Int {
        return numOfTabs
    }

    //RETURNS THE FRAGMENT SELECTED IN THE TAB LAYOUT
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Page1Fragment()
            1 -> Page2Fragment()
            2 -> Page3Fragment()
            else -> Page1Fragment()
        }
    }
}