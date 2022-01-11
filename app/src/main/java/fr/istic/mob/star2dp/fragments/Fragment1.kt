package fr.istic.mob.star2dp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.databinding.Fragment1Binding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.util.CustomAdapter
import fr.istic.mob.star2dp.util.Intermediate
import fr.istic.mob.star2dp.util.Utils
import java.util.*

class Fragment1 : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var binding: Fragment1Binding? = null
    private lateinit var intermadiate: Intermediate

    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var lines: Spinner
    private lateinit var stops: Spinner

    private var day = 0
    private var month = 0
    private var year = 0
    private var minutes = 0
    private var hours = 0

    private var choosedDate: String? = null
    private var chosedTime: String? = null

    private var selectedBusRoutes: BusRoutes? = null
    private var selectedBusRoutesFullName: String? = null
    private var selectedBusRoutesShortName: String? = null

    private var selectedTerminus: String? = null
    private var listTerminus: List<String>? = null
    private lateinit var btnNext: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = Fragment1Binding.inflate(inflater, container, false)
        val view = binding!!.root

        intermadiate = activity as Intermediate

        btnDate = view.findViewById(R.id.chooseDateBtn)
        btnTime = view.findViewById(R.id.chooseHourBtn)
        btnNext = view.findViewById(R.id.nextBtn)
        btnNext.isClickable = false
        lines = view.findViewById(R.id.lignesSpin)
        stops = view.findViewById(R.id.terminusSpin)

        setPickers()

        stops.visibility = View.GONE
        activity?.let { Utils.setContext(it) }
        val linesArrayAdapter = CustomAdapter(
            this.requireActivity(),
            R.layout.item, Utils.getBusRoute()!!,
            R.id.myTextView,
            R.id.textParent
        )
        lines.adapter = linesArrayAdapter
        val context = this.requireContext()
        lines.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    selectedBusRoutes = parent?.getItemAtPosition(position) as BusRoutes

                    selectedBusRoutesFullName = Utils.removeQuotes(selectedBusRoutes!!.longName)
                    selectedBusRoutesShortName = Utils.removeQuotes(selectedBusRoutes!!.shortName)

                    Log.i("", "$selectedBusRoutesFullName")
                    listTerminus = Utils.getTerminus(selectedBusRoutesFullName!!)
                    Log.i("", "${listTerminus.toString()}")
                    if (listTerminus != null) {
                        stops.visibility = View.VISIBLE
                        val stopsArrayAdapter = ArrayAdapter(
                            context,
                            R.layout.support_simple_spinner_dropdown_item,
                            listTerminus!!
                        )
                        stops.adapter = stopsArrayAdapter
                    }
                } else {
                    stops.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        stops.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    selectedTerminus = parent?.getItemAtPosition(position) as String
                    btnNext.isClickable = true
                    println()
                    Log.i("", "$selectedTerminus")
                    println()
                } else {
                    btnNext.isClickable = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btnNext.setOnClickListener {

            if (selectedBusRoutesFullName != null && selectedTerminus != null) {
                val data =
                    hashMapOf(
                        "lineLongName" to selectedBusRoutesFullName!!,
                        "lineShortName" to selectedBusRoutesShortName!!,
                        "terminus" to selectedTerminus!!,
                        "date" to choosedDate!!,
                        "time" to chosedTime!!
                    )

                val fragment2 = Fragment2.newInstance()
                intermadiate.sendData(fragment2, data)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun setPickers() {
        getCalendarObject()

        btnDate.setOnClickListener {
            getCalendarObject()
            DatePickerDialog(this.requireContext(), this, year, month, day).show()
        }

        btnTime.setOnClickListener {
            getCalendarObject()
            TimePickerDialog(this.requireContext(), this, hours, minutes, true).show()
        }
    }

    private fun getCalendarObject() {
        val cal: Calendar = Calendar.getInstance()

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        minutes = cal.get(Calendar.MINUTE)
        hours = cal.get(Calendar.HOUR_OF_DAY)

        choosedDate = "$day ${formatMonth(month)} $year"
        btnDate.text = choosedDate
        chosedTime = "$hours : $minutes"
        btnTime.text = chosedTime
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        choosedDate = "$dayOfMonth ${formatMonth(month)} $year"
        btnDate.text = choosedDate
    }

    private fun formatMonth(month: Int): String {
        when (month + 1) {
            1 -> {
                return "Janvier"
            }
            2 -> {
                return "Fevrier"
            }
            3 -> {
                return "Mars"
            }
            4 -> {
                return "Avril"
            }
            5 -> {
                return "Mai"
            }
            6 -> {
                return "Juin"
            }
            7 -> {
                return "Juillet"
            }
            8 -> {
                return "Août"
            }
            9 -> {
                return "Septembre"
            }
            10 -> {
                return "Octobre"
            }
            11 -> {
                return "Novembre"
            }
            12 -> {
                return "Decembre"
            }
            else -> {
                return "####"
            }
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        chosedTime = "$hourOfDay : $minute"
        btnTime.text = chosedTime
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {

            }
    }
}