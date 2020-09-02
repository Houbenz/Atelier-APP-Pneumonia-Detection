package com.houbenz.deeplearning.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.houbenz.deeplearning.R

class ResultsFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var resultFragmentStateAdapter: ResultsFragmentStateAdapter
    private lateinit var viewPager:ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_results, container, false)
        return root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.pager)

        resultFragmentStateAdapter =ResultsFragmentStateAdapter(this)

        viewPager.adapter=resultFragmentStateAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text="Matrice de confusion"
                1 -> tab.text="Evolution d'entrainement"
                2 -> tab.text="Temps d'entrainement"
            }

        }.attach()
    }
}