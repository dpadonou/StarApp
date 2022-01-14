package fr.istic.mob.star2dp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import fr.istic.mob.star2dp.models.BusRoutes

class CustomAdapter : BaseAdapter {

    val context: Activity
    val layoutId: Int
    val itemId: Int
    val itemParentId: Int
    val elements: List<BusRoutes>

    constructor(
        context: Activity,
        layoutId: Int,
        elements: List<BusRoutes>,
        itemId: Int,
        itemParentId: Int,
    ) : super() {
        this.context = context
        this.layoutId = layoutId
        this.elements = elements
        this.itemId = itemId
        this.itemParentId = itemParentId
    }

    override fun getCount(): Int {
        if (elements == null) {
            return 0
        }
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
        val mText = mView.findViewById<TextView>(this.itemId)
        val mTextparent = mView.findViewById<LinearLayout>(this.itemParentId)
        val mRoute = getItem(position) as BusRoutes
        if (mRoute.color.equals("")) {
            mText.setTextColor(Color.parseColor("#000000"))
        } else {
            mText.setTextColor(Color.parseColor(Utils.appendHashtag(mRoute.color)))
        }
        mText.text = mRoute.shortName
        // mTextparent.setBackgroundColor(Color.parseColor("#" + Utils.removeQuotes(mRoute.color)))
        return mView
    }


}