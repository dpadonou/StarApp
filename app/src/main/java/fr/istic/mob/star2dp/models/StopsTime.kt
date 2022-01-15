package fr.istic.mob.star2dp.models

class StopsTime {
    var id:Int = 0
    var tripId:String = ""
    var arrivalTime:String = ""
    var departureTime:String = ""
    var stopId:String = ""
    var stopSequence:String = ""

    constructor(
        id: Int,
        tripId: String,
        arrivalTime: String,
        departureTime: String,
        stopId: String,
        stopSequence: String
    ) {
        this.id = id
        this.tripId = tripId
        this.arrivalTime = arrivalTime
        this.departureTime = departureTime
        this.stopId = stopId
        this.stopSequence = stopSequence
    }
}