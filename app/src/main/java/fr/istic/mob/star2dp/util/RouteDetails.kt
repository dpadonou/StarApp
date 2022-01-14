package fr.istic.mob.star2dp.util

class RouteDetails {
    var name: String = ""
    var hour: String = ""

    constructor(name: String, hour: String) {
        this.name = name
        this.hour = hour
    }

    override fun toString(): String {
        return "$name \t\t\t\t\t\t\t ${CalendarUtils.beautifyHour(hour)}"
    }
}