package fr.istic.mob.star2dp.custom_classes

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.util.Utils

class CustomGridViewAdapter: BaseAdapter {

    val context: Activity
    val layoutId: Int
    val itemId: Int
    val elements: List<BusRoutes>

    constructor(
        context: Activity,
        layoutId: Int,
        elements: List<BusRoutes>,
    itemId: Int,
    ) : super() {
        this.context = context
        this.layoutId = layoutId
        this.elements = elements
        this.itemId = itemId
    }

    override fun getCount(): Int {
        return elements.size
    }

    override fun getItem(position: Int): Any {
        return this.elements.get(position)
    }

    override fun getItemId(position: Int): Long {
        val b = this.getItem(position) as BusRoutes
        return b._id.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val mView = this.context.layoutInflater.inflate(this.layoutId, null, true)

        val mRoute = getItem(position) as BusRoutes
        val mBtn = mView.findViewById(this.itemId) as TextView

        if (mRoute.color.equals("")) {
            mBtn.setBackgroundColor(Color.parseColor("#000000"))
        } else {
            mBtn.setBackgroundColor(Color.parseColor(Utils.appendHashtag(mRoute.color)))
        }

        mBtn.text = mRoute.shortName

        return mView
    }

}