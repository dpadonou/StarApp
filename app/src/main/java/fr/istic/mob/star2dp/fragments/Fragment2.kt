package fr.istic.mob.star2dp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import fr.istic.mob.star2dp.databinding.Fragment2Binding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.models.Stops
import fr.istic.mob.star2dp.util.Terminus
import fr.istic.mob.star2dp.util.Utils

class Fragment2 : Fragment() {

    private var binding: Fragment2Binding? = null

    var data: HashMap<String, Any>? = null

    private var selectedStops: Stops? = null

    private lateinit var selectedLine: TextView
    private lateinit var selectedTerminus: TextView
    private lateinit var selectedDateTextView: TextView
    private lateinit var selectedTimeTextView: TextView
    private lateinit var stopsListView: ListView
    private lateinit var stopsList: List<Stops>

    private lateinit var searchTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = Fragment2Binding.inflate(inflater, container, false)
        val view = binding!!.root

        searchTextView = view.findViewById(R.id.search_text_view)

        if (arguments != null) {
            data = arguments?.getSerializable("data") as HashMap<String, Any>
            for (s in data!!.keys) {
                println("$s : ${data!![s]}")
            }
        }

        if (data != null) {
            selectedLine = view.findViewById(R.id.bus_route_text_2)
            selectedTerminus = view.findViewById(R.id.bus_direction_text_2)
            selectedDateTextView = view.findViewById(R.id.selected_date_text_view_2)
            selectedTimeTextView = view.findViewById(R.id.selected_time_text_view_2)
            stopsListView = view.findViewById(R.id.stopsListView)

            selectedLine.text = (data!!["line"] as BusRoutes).shortName
            selectedTerminus.text = (data!!["terminus"] as Terminus).name
            selectedDateTextView.text = data!!["chosenDate"] as String
            selectedTimeTextView.text = data!!["chosenTime"] as String
        }

        stopsList = Utils.getStop((data!!["line"] as BusRoutes).routeId,
            (data!!["terminus"] as Terminus).id)!!
        val stopsArrayAdapter = ArrayAdapter(
            this.requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            stopsList
        )
        stopsListView.adapter = stopsArrayAdapter

        val savedList = Utils.getStop((data!!["line"] as BusRoutes).routeId,
            (data!!["terminus"] as Terminus).id)!!

        searchTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                newValue: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                if (newValue.isNotEmpty()) {
                    val newList = savedList.filter { stops ->
                        stops.stopName.lowercase().contains(newValue.toString().lowercase())
                    }

                    stopsArrayAdapter.clear()
                    stopsArrayAdapter.addAll(newList)
                    stopsListView.adapter = stopsArrayAdapter
                    stopsListView.invalidate()
                } else {
                    stopsArrayAdapter.clear()
                    stopsArrayAdapter.addAll(savedList)
                    stopsListView.adapter = stopsArrayAdapter
                    stopsListView.invalidate()
                }
            }
        })

        stopsListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedStops = parent?.getItemAtPosition(position) as Stops
                if (selectedStops != null) {
                    data!!["stop"] = selectedStops!!
                    Navigation.findNavController(view)
                        .navigate(R.id.go_to_third, Utils.sendData(data!!))
                }
            }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Fragment2().apply {
            }
    }
}