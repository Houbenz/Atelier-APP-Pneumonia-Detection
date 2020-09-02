package com.houbenz.deeplearning.ui.results

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.houbenz.deeplearning.ui.results.tabs.ConfusionMatrixFragment
import com.houbenz.deeplearning.ui.results.tabs.TrainingHistory
import com.houbenz.deeplearning.ui.results.tabs.TrainingTimeFragment

class ResultsFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){



    override fun createFragment(position: Int): Fragment {

        when(position){
            0 -> return ConfusionMatrixFragment()
            1 -> return TrainingHistory()
            2 -> return TrainingTimeFragment()
         else -> return ConfusionMatrixFragment()
        }


    }

    override fun getItemCount(): Int = 3
}