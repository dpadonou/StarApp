package fr.istic.mob.star2dp.custom_classes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.databinding.Fragment1Binding
import fr.istic.mob.star2dp.fragments.SearchResultFragment
import fr.istic.mob.star2dp.util.Utils


class CustomDialog : DialogFragment() {

    var data: HashMap<String, Any>? = null

    private lateinit var stopsTextView: TextView
    private lateinit var stopsListView: ListView

    private lateinit var stopsList: List<String>
    private var selectedStops: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view = inflater.inflate(R.layout.find_stops, container, false)

        (view.findViewById(R.id.btn_close) as Button).setOnClickListener {
            dismiss()
        }

        stopsListView = view.findViewById(R.id.find_stops_list_view)
        stopsTextView = view.findViewById(R.id.find_stops_text_view)

        stopsTextView.addTextChangedListener(object : TextWatcher {
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
                    stopsList = Utils.searchStop(newValue.toString())

                    val stopsArrayAdapter = context?.let {
                        ArrayAdapter(
                            it,
                            R.layout.support_simple_spinner_dropdown_item,
                            stopsList
                        )
                    }

                    stopsListView.adapter = stopsArrayAdapter
                    stopsListView.invalidate()
                }
            }
        })

        stopsListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                dismiss()
                selectedStops = parent?.getItemAtPosition(position) as String
                if (selectedStops != null) {
                    data = hashMapOf("stop" to selectedStops!!)
                    NavHostFragment.findNavController(this).navigate(R.id.go_to_result, Utils.sendData(data!!))
                }
            }

        return view
    }

    companion object{
        @JvmStatic
        fun newInstance() = CustomDialog()
    }
}