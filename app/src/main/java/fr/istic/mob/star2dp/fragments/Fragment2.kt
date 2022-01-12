package fr.istic.mob.star2dp.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.databinding.Fragment2Binding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.util.CalendarUtils
import fr.istic.mob.star2dp.util.Intermediate
import fr.istic.mob.star2dp.util.Terminus
import java.time.LocalDate
import java.time.LocalTime

class Fragment2 : Fragment() {

    private var binding: Fragment2Binding? = null
    private lateinit var intermadiate: Intermediate

    var data: HashMap<String, Any>? = null

    private lateinit var selectedLine: TextView
    private lateinit var selectedTerminus: TextView
    private lateinit var selectedDateTextView: TextView
    private lateinit var selectedTimeTextView: TextView
    private lateinit var listStops: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = Fragment2Binding.inflate(inflater, container, false)
        val view = binding!!.root

        data = arguments?.getSerializable("data") as HashMap<String, Any>
        for (s in data!!.keys) {
            println("$s : ${data!![s]}")
        }

        if (data != null) {
            selectedLine = view.findViewById(R.id.bus_route)
            selectedTerminus = view.findViewById(R.id.bus_direction)
            selectedDateTextView = view.findViewById(R.id.selected_date_text_view)
            selectedTimeTextView = view.findViewById(R.id.selected_time_text_view)
            listStops = view.findViewById(R.id.stopsListView)

            selectedLine.text = (data!!["line"] as BusRoutes).shortName
            selectedTerminus.text = (data!!["terminus"] as Terminus).name
            selectedDateTextView.text = data!!["chosenDate"] as String
            selectedTimeTextView.text = data!!["chosenTime"] as String
        }

        /*val stopsArrayAdapter = ArrayAdapter(
            context,
            R.layout.simple_list_,
            listTerminus!!
        )
        stops.adapter = stopsArrayAdapter*/

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Fragment2().apply {
                arguments = Bundle().apply {

                }
            }
    }
}