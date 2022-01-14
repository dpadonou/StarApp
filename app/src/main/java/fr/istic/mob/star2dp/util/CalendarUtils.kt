package fr.istic.mob.star2dp.util

import java.util.*

class CalendarUtils {

    companion object {

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

        fun displayDateFormat(day: Int, month: Int, year: Int): String {
            return "$day ${formatMonth(month)} $year"
        }

        fun displayTimeFormat(hour: Int, minute: Int): String {
            return "$hour : $minute"
        }

        fun formatHour(hour: Int, minute: Int, second: Int): String {
            return "$hour:$minute:$second"
        }

        fun formatDate(year: Int, month: Int, day: Int): String {
            return "$year$month$day"
        }

        fun getDayName(day: Int): String {
            return when (day) {
                Calendar.SUNDAY -> {
                    "sunday"
                }
                Calendar.MONDAY -> {
                    "monday"
                }
                Calendar.TUESDAY -> {
                    "tuesday"
                }
                Calendar.WEDNESDAY -> {
                    "wednesday"
                }
                Calendar.THURSDAY -> {
                    "thursday"
                }
                Calendar.FRIDAY -> {
                    "friday"
                }
                Calendar.SATURDAY -> {
                    "saturday"
                }
                else -> ""
            }
        }

        fun beautifyHour(hour: String): String{
            return hour.dropLast(3).replace(':', 'h')
        }
    }
}