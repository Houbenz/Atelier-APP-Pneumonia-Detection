package com.houbenz.deeplearning.ui.results.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.houbenz.deeplearning.R


class ConfusionMatrixFragment : Fragment() {

     class Model(var tp:Int,var tn:Int,var fp:Int,var fn:Int,var name: String)

    private lateinit var anyChart: AnyChartView
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_confusion_matrix, container, false)





        recyclerView=root.findViewById(R.id.recycler)
        recyclerView.adapter=RecyclerAdapter(getModels())

        recyclerView.layoutManager=LinearLayoutManager(context)

        return root
    }


    fun getModels():ArrayList<Model> {

        val models =arrayListOf<Model>()

        models.add(Model(376,206,28,1,"Resnet"))
        models.add(Model(362,213,21,28,"VGG16"))
        models.add(Model(372,213,21,18,"Inception"))
        models.add(Model(385,197,37,5,"MobileNet"))
        models.add(Model(373,209,25,17,"EfficientNet"))
        return models
    }

}