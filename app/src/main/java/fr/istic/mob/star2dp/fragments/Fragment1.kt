package fr.istic.mob.star2dp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.custom_classes.CustomAdapter
import fr.istic.mob.star2dp.databinding.Fragment1Binding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.util.CalendarUtils
import fr.istic.mob.star2dp.util.Terminus
import fr.istic.mob.star2dp.util.Utils
import java.util.*

class Fragment1 : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var binding: Fragment1Binding? = null
    var data: HashMap<String, Any>? = null

    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var lines: Spinner
    private lateinit var stops: Spinner

    private var day = 0
    private var month = 0
    private var year = 0
    private var minutes = 0
    private var hours = 0

    private var chosenDate: String? = null
    private var chosenDay: String? = null
    private var chosenTime: String? = null
    private var fullChosenTime: String? = null
    private var formattedChosenDate: String? = null

    private var selectedBusRoutes: BusRoutes? = null
    private var selectedBusRoutesFullName: String? = null

    private lateinit var linesArrayAdapter: CustomAdapter

    private var selectedTerminus: Terminus? = null

    private var listTerminus: List<Terminus>? = null
    private lateinit var btnNext: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = Fragment1Binding.inflate(inflater, container, false)
        val view = binding!!.root

        if (arguments != null) {
            data = arguments?.getSerializable("data") as HashMap<String, Any>
            for (s in data!!.keys) {
                println("$s : ${data!![s]}")
            }
        }

        btnDate = view.findViewById(R.id.chooseDateBtn)
        btnTime = view.findViewById(R.id.chooseHourBtn)
        btnNext = view.findViewById(R.id.nextBtn)
        btnNext.isClickable = false
        btnNext.setBackgroundColor(Color.parseColor("#0FC1A1"))
        lines = view.findViewById(R.id.lignesSpin)
        stops = view.findViewById(R.id.terminusSpin)

        setPickers()

        stops.visibility = View.GONE
        activity?.let { Utils.setContext(it) }

        linesArrayAdapter = CustomAdapter(
            this.requireActivity(),
            R.layout.item,
            Utils.getBusRoute(),
            R.id.myTextview,
            R.id.textParent
        )
        lines.adapter = linesArrayAdapter

        val context = this.requireContext()
        lines.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (position != 0) {
                    selectedBusRoutes = parent?.getItemAtPosition(position) as BusRoutes

                    selectedBusRoutesFullName = selectedBusRoutes!!.longName
                    Log.i("", "$selectedBusRoutesFullName")

                    listTerminus = Utils.getTerminus(selectedBusRoutesFullName!!)
                    Log.i("", "$listTerminus")

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
                id: Long,
            ) {
                if (position != 0) {
                    selectedTerminus = parent?.getItemAtPosition(position) as Terminus

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
                        "line" to selectedBusRoutes!!,
                        "terminus" to selectedTerminus!!,
                        "chosenDay" to chosenDay!!,
                        "chosenDate" to chosenDate!!,
                        "formattedChosenDate" to formattedChosenDate!!,
                        "chosenTime" to chosenTime!!,
                        "fullChosenTime" to fullChosenTime!!,
                    )

                Navigation.findNavController(view).navigate(R.id.go_to_second, Utils.sendData(data))
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()

        if (data != null) {
            val b = data!!["chosenLine"] as BusRoutes
            val pos = linesArrayAdapter.getItemPosition(b)
            lines.setSelection(pos)
        }
    }

    private fun setPickers() {
        getCalendarObject()
        btnDate.setOnClickListener {
            DatePickerDialog(this.requireContext(), this, year, month, day).show()
        }

        btnTime.setOnClickListener {
            TimePickerDialog(this.requireContext(), this, hours, minutes, true).show()
        }
    }

    private fun getCalendarObject() {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+01"))

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        minutes = cal.get(Calendar.MINUTE)
        hours = cal.get(Calendar.HOUR_OF_DAY)

        chosenDay = CalendarUtils.getDayName(cal.get(Calendar.DAY_OF_WEEK))
        chosenDate = CalendarUtils.displayDateFormat(day, month, year)
        formattedChosenDate = CalendarUtils.formatDate(year, month + 1, day)
        btnDate.text = CalendarUtils.displayDateFormat(day, month, year)

        chosenTime = CalendarUtils.displayTimeFormat(hours, minutes)
        fullChosenTime = CalendarUtils.formatHour(hours, minutes, cal.get(Calendar.SECOND))
        btnTime.text = chosenTime
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        cal.set(year, month, dayOfMonth)

        this.day = cal.get(Calendar.DAY_OF_MONTH)
        this.month = cal.get(Calendar.MONTH)
        this.year = cal.get(Calendar.YEAR)

        chosenDay = CalendarUtils.getDayName(cal.get(Calendar.DAY_OF_WEEK))
        chosenDate = CalendarUtils.displayDateFormat(dayOfMonth, month, year)
        formattedChosenDate = CalendarUtils.formatDate(year, month + 1, dayOfMonth)

        btnDate.text = CalendarUtils.displayDateFormat(dayOfMonth, month, year)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        chosenTime = CalendarUtils.displayTimeFormat(hours, minutes)
        fullChosenTime = CalendarUtils.formatHour(hours, minutes, Calendar.SECOND)

        btnTime.text = chosenTime
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