package fr.istic.mob.star2dp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.databinding.Fragment2Binding
import fr.istic.mob.star2dp.util.Intermediate

class Fragment2 : Fragment() {

    private var binding: Fragment2Binding? = null
    private lateinit var intermadiate: Intermediate

    var data: HashMap<String, String>? = null

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

        data = arguments?.getSerializable("data") as HashMap<String, String>
        for (s in data!!.keys) {
            println("$s : ${data!![s]}")
        }

        selectedLine = view.findViewById(R.id.bus_route)
        selectedTerminus = view.findViewById(R.id.bus_direction)
        selectedDateTextView = view.findViewById(R.id.selected_date_text_view)
        selectedTimeTextView = view.findViewById(R.id.selected_time_text_view)
        listStops = view.findViewById(R.id.stopsListView)

        selectedLine.text = data!!["lineShortName"]
        selectedTerminus.text = data!!["terminus"]
        selectedDateTextView.text = data!!["date"]
        selectedTimeTextView.text = data!!["time"]

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