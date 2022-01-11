package fr.istic.mob.star2dp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.istic.mob.star2dp.R
import fr.istic.mob.star2dp.databinding.Fragment1Binding
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.util.Utils
import java.util.*

class Fragment1 : Fragment(), DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener{

    private var binding : Fragment1Binding? = null

    private lateinit var dateDialog: DatePickerDialog
    private lateinit var timeDialog: TimePickerDialog
    private lateinit var btnDate: Button
    private lateinit var btnTime: Button

    private var day = 0
    private var month = 0
    private var year = 0
    private var minutes = 0
    private var hours = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = Fragment1Binding.inflate(inflater, container, false)
        var view = binding!!.root


        btnDate = view.findViewById(R.id.chooseDateBtn)
        btnTime = view.findViewById(R.id.chooseHourBtn)

        //val busRoutes = Utils.getBusRoute()
        //val adapter = ArrayAdapter<BusRoutes>(this, R.layout.support_simple_spinner_dropdown_item, busRoutes)

        setPickers()

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {

            }
    }

    private fun setPickers() {
        btnDate.setOnClickListener {
            getCalendarObject()
            DatePickerDialog(this.requireContext(), this, year, month, day).show()
        }

        btnTime.setOnClickListener{
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
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val newText = "$dayOfMonth ${formatMonth(month)} $year"
        btnDate.text = newText
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
        val newText = "$hourOfDay : $minute"
        btnTime.text = newText
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}