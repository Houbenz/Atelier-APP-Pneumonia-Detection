package com.houbenz.deeplearning.ui.results.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.houbenz.deeplearning.R


class TrainingTimeFragment : Fragment() {


    private lateinit var anychart:AnyChartView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_training_time, container, false)

        anychart=root.findViewById(R.id.chartColumn)


       /* var data =arrayListOf<DataEntry>()

        data.add(ValueDataEntry("RESNET",20055))
        data.add(ValueDataEntry("MOBILENETV2",13005))
        data.add(ValueDataEntry("INCEPTION",15066))
        data.add(ValueDataEntry("VGG16",8006))
        data.add(ValueDataEntry("EfficientNetB",12036))
*/

        val cartesian = AnyChart.column()
        val column = cartesian.column(getData())
        column.tooltip()
            .titleFormat("{%X}")
            .offsetX(0.0)
            .offsetY(5.0)
            .format("{%Value}{groupsSeparator: }")
        cartesian.animation(true)
        cartesian.yScale().minimum(0.0)
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }s")
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)
        cartesian.xAxis(0).title("Modèle")
        cartesian.yAxis(0).title("Temps (1000s)")
        cartesian.xAxis(0).labels().fontWeight("bold")
        cartesian.xAxis(0).labels().fontColor("#000000")
        cartesian.xAxis(0).labels().fontSize(8)
        anychart.setChart(cartesian)

        return root
    }

    private fun getData(): MutableList<DataEntry> {
        val entries: MutableList<DataEntry> = ArrayList()
        entries.add(ValueDataEntry("Resnet 50",20.055))
        entries.add(ValueDataEntry("Mobile Net",13.005))
        entries.add(ValueDataEntry("Inception",15.066))
        entries.add(ValueDataEntry("VGG 16",8.006))
        entries.add(ValueDataEntry("Efficient Net",12.036))
        return entries
    }

}
