package fr.istic.mob.star2dp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.custom_classes.RouteDetailsAdapter
import fr.istic.mob.star2dp.databinding.Fragment4Binding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.models.Stops
import fr.istic.mob.star2dp.models.StopsTime
import fr.istic.mob.star2dp.util.*

class Fragment4 : Fragment() {

    private var binding: Fragment4Binding? = null
    var data: HashMap<String, Any>? = null

    private var routeDetailsList: List<RouteDetails>? = null
    private lateinit var routeDetailsListView: ListView

    private lateinit var selectedLine: TextView
    private lateinit var selectedTerminus: TextView
    private lateinit var selectedTimeTextView: TextView
    private lateinit var selectedStopTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment4Binding.inflate(inflater, container, false)
        val view = binding!!.root

        routeDetailsListView = view.findViewById(R.id.route_details_list_view)

        data = arguments?.getSerializable("data") as HashMap<String, Any>
        for (s in data!!.keys) {
            println("$s : ${data!![s]}")
        }

        if (data != null) {
            selectedLine = view.findViewById(R.id.bus_route_text_4)
            selectedTerminus = view.findViewById(R.id.bus_direction_text_4)
            selectedTimeTextView = view.findViewById(R.id.selected_departure_text_view)
            selectedStopTextView = view.findViewById(R.id.stop_text_4)

            selectedLine.text = (data!!["line"] as BusRoutes).shortName
            selectedTerminus.text = (data!!["terminus"] as Terminus).name
            selectedTimeTextView.text = CalendarUtils.beautifyHour((data!!["stopTime"] as StopsTime).arrivalTime)
            selectedStopTextView.text = (data!!["stop"] as Stops).stopName
        }

        val params =
            mapOf(
                "trip_id" to (data!!["stopTime"] as StopsTime).tripId,
                "hour" to (data!!["stopTime"] as StopsTime).arrivalTime
            )
        routeDetailsList = Utils.getRouteDetails(params)

        /*val routeDetailsArrayAdapter = ArrayAdapter(
            this.requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            routeDetailsList!!
        )*/

        val routeDetailsArrayAdapter = RouteDetailsAdapter(
            this.requireActivity(),
            R.layout.route_details_row,
            routeDetailsList!!
        )

        routeDetailsListView.adapter = routeDetailsArrayAdapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Fragment4().apply {
            }
    }
}