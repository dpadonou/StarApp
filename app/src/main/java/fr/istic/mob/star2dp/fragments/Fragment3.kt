package fr.istic.mob.star2dp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.databinding.Fragment3Binding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.models.Stops
import fr.istic.mob.star2dp.models.StopsTime
import fr.istic.mob.star2dp.util.Terminus
import fr.istic.mob.star2dp.util.Utils

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment() {
    private var binding: Fragment3Binding? = null
    var data: HashMap<String, Any>? = null

    private var selectedStopTimes: StopsTime? = null
    private var stopsTimesList: List<StopsTime>? = null
    private lateinit var stopsTimesListView: ListView

    private lateinit var selectedLine: TextView
    private lateinit var selectedTerminus: TextView
    private lateinit var selectedDateTextView: TextView
    private lateinit var selectedTimeTextView: TextView
    private lateinit var selectedStopTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = Fragment3Binding.inflate(inflater, container, false)
        val view = binding!!.root

        stopsTimesListView = view.findViewById(R.id.stop_times_list_view)

        data = arguments?.getSerializable("data") as HashMap<String, Any>
        for (s in data!!.keys) {
            println("$s : ${data!![s]}")
        }

        if (data != null) {
            selectedLine = view.findViewById(R.id.bus_route_text_3)
            selectedTerminus = view.findViewById(R.id.bus_direction_text_3)
            selectedDateTextView = view.findViewById(R.id.selected_date_text_view_3)
            selectedTimeTextView = view.findViewById(R.id.selected_time_text_view_3)
            selectedStopTextView = view.findViewById(R.id.stop_text_3)

            selectedLine.text = (data!!["line"] as BusRoutes).shortName
            selectedTerminus.text = (data!!["terminus"] as Terminus).name
            selectedDateTextView.text = data!!["chosenDate"] as String
            selectedTimeTextView.text = data!!["chosenTime"] as String
            selectedStopTextView.text = (data!!["stop"] as Stops).stopName
        }
        val params = mapOf(
            "route_id" to (data!!["line"] as BusRoutes).routeId,
            "stop_id" to (data!!["stop"] as Stops).stopId,
            "direction_id" to (data!!["terminus"] as Terminus).id,
            "day" to data!!["chosenDay"] as String,
            "arrival_time" to data!!["fullChosenTime"] as String
        )
        stopsTimesList = Utils.getStopTimes(params)

        val stopsTimesArrayAdapter = ArrayAdapter(
            this.requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            stopsTimesList!!
        )
        stopsTimesListView.adapter = stopsTimesArrayAdapter

        stopsTimesListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedStopTimes = parent?.getItemAtPosition(position) as StopsTime
                if (selectedStopTimes != null) {
                    data!!["stopTime"] = selectedStopTimes!!
                    Navigation.findNavController(view).navigate(R.id.go_to_forth, Utils.sendData(data!!))
                }
            }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Fragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            Fragment3().apply {
            }
    }
}