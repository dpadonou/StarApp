package fr.istic.mob.star2dp

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import fr.istic.mob.star2dp.models.BusRoutes

class MyAdapter:BaseAdapter {
    val context:Activity
    val layoutId:Int
    val itemId:Int
    val itemParentId:Int
    val elements:List<BusRoutes>
    constructor(context: Activity, layoutId:Int, elements:List<BusRoutes>,itemId:Int,itemParentId:Int) : super(){
       this.context = context
       this.layoutId = layoutId
       this.elements = elements
       this.itemId = itemId
       this.itemParentId = itemParentId
    }

    override fun getCount(): Int {
        if(elements == null){
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val mView = this.context.layoutInflater.inflate(this.layoutId,null,true)
        val mText = mView.findViewById<TextView>(this.itemId)
        val mTextparent = mView.findViewById<LinearLayout>(this.itemParentId)
        val mRoute = getItem(position) as BusRoutes
        mText.text =mRoute.shortName
        mText.setTextColor(Color.parseColor("#"+Utils.formatString(mRoute.textColor)))
        mTextparent.setBackgroundColor(Color.parseColor("#"+Utils.formatString(mRoute.color)))
        return mView
    }
}