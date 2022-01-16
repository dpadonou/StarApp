package fr.istic.mob.star2dp.custom_classes

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.util.CalendarUtils
import fr.istic.mob.star2dp.util.RouteDetails

class RouteDetailsAdapter : BaseAdapter {

    val context: Activity
    val layoutId: Int
    val elements: List<RouteDetails>

    constructor(
        context: Activity,
        layoutId: Int,
        elements: List<RouteDetails>,
    ) : super() {
        this.context = context
        this.layoutId = layoutId
        this.elements = elements
    }

    override fun getCount(): Int {
        return elements.size
    }

    override fun getItem(position: Int): Any {
        return this.elements[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val mView = this.context.layoutInflater.inflate(layoutId, null, true)
        val stop = mView.findViewById<TextView>(R.id.route_details_text_1)
        val hour = mView.findViewById<TextView>(R.id.route_details_text_2)
        val routeDetails = getItem(position) as RouteDetails

        stop.text = routeDetails.name
        hour.text = CalendarUtils.beautifyHour(routeDetails.hour)
        return mView
    }
}