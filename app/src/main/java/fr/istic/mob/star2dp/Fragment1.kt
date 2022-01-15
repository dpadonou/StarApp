package fr.istic.mob.star2dp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import fr.istic.mob.star2dp.models.BusRoutes


class Fragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_1, container, false)
        val routes = activity?.let { Utils(it).getBusRoute() }
        val adapter = MyAdapter(this.requireActivity(),R.layout.item, routes!!,R.id.mytextview,R.id.textParent)
        mView.findViewById<Spinner>(R.id.spinner_line)?.adapter = adapter
        mView.findViewById<Spinner>(R.id.spinner_line)?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                val adapter: Adapter = parent.adapter
                val route = adapter.getItem(position) as BusRoutes
                val s1 = Utils.formatString(route.longName)
                val s2 = Utils.formatString(route.color)
                Log.i("","$s1 -> $s2 -> ${route.type}")
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        return mView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {

            }
    }
}