package fr.istic.mob.star2dp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.custom_classes.CustomAdapter
import fr.istic.mob.star2dp.custom_classes.CustomGridViewAdapter
import fr.istic.mob.star2dp.databinding.FragmentSearchResultBinding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.util.Utils

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultFragment : Fragment() {

    var data: HashMap<String, Any>? = null

    private var binding: FragmentSearchResultBinding? = null
    private var selectedBusRoutes: BusRoutes? = null
    private lateinit var busRouteGridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val view = binding!!.root

        busRouteGridView = view.findViewById(R.id.line_grid)

        if (arguments != null) {
            data = arguments?.getSerializable("data") as HashMap<String, Any>
            for (s in data!!.keys) {
                println("$s : ${data!![s]}")
            }
        }

        if (data != null) {
            val busRoutesArrayAdapter = CustomGridViewAdapter(
                this.requireActivity(),
                R.layout.grid_item,
                (Utils.getBusRoute(Utils.escapeQuotes((data!!["stop"] as String)))!!).drop(1),
                R.id.line_btn
            )
            busRouteGridView.adapter = busRoutesArrayAdapter
        }

        busRouteGridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedBusRoutes = parent?.getItemAtPosition(position) as BusRoutes
                if (selectedBusRoutes != null) {
                    data!!.clear()
                    data!!["chosenLine"] = selectedBusRoutes!!
                    Navigation.findNavController(view).navigate(R.id.go_to_first, Utils.sendData(data!!))
                }
            }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}