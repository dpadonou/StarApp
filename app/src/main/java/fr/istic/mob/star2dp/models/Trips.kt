package fr.istic.mob.star2dp.models

class Trips {

    var id:Int = 0
    var tripId:String = ""
    var routeId:String = ""
    var serviceId:String = ""
    var headSign:String = ""
    var directionId:String = ""
    var blockId:String = ""
    var wheelChairAccessible:String = ""

    constructor(
        id: Int,
        routeId: String,
        tripId:String,
        serviceId: String,
        headSign: String,
        directionId: String,
        blockId: String,
        wheelChairAccessible: String
    ) {
        this.id = id
        this.tripId
        this.routeId = routeId
        this.serviceId = serviceId
        this.headSign = headSign
        this.directionId = directionId
        this.blockId = blockId
        this.wheelChairAccessible = wheelChairAccessible
    }

}