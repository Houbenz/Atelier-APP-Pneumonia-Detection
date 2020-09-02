package com.houbenz.deeplearning.ui.results.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.houbenz.deeplearning.R
class RecyclerAdapter(var arrayList: ArrayList<ConfusionMatrixFragment.Model>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.confusion_matrix_element,parent,false)

        return  ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val anychartview=holder.itemView.findViewById<AnyChartView>(R.id.chart_matrix)


        val pie: Pie = AnyChart.pie()

        pie.title().enabled(true)
        pie.title().text(arrayList.get(position).name)
        pie.title().fontColor("white")
        pie.title().fontSize(24)
        pie.title().background().enabled(true)
        pie.title().padding(5)
        pie.title().background().fill("#FF0000")
        pie.title().background().cornerType("round")
        pie.title().background().corners(10)

        pie.background().fill("#82b3c9 0.2")

        val data =arrayListOf<DataEntry>()
        data.add(ValueDataEntry("True Positives",arrayList.get(position).tp))
        data.add(ValueDataEntry("True Negatives",arrayList.get(position).tn))
        data.add(ValueDataEntry("False Positives",arrayList.get(position).fp))
        data.add(ValueDataEntry("False Negatives",arrayList.get(position).fn))
        pie.animation(true)

        pie.data(data)

        anychartview.setChart(pie)

    }

    override fun getItemCount(): Int = 5

}
