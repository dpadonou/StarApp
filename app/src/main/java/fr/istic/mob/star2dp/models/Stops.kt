package fr.istic.mob.star2dp.models

class Stops {
    var id:Int = 0
    var stopName:String = ""
    var description:String = ""
    var latitude:String = ""
    var longitutde:String = ""
    var wheelChairBoarding:String = ""

    constructor(
        id: Int,
        stopName: String,
        description: String,
        latitude: String,
        longitutde: String,
        wheelChairBoarding: String
    ) {
        this.id = id
        this.stopName = stopName
        this.description = description
        this.latitude = latitude
        this.longitutde = longitutde
        this.wheelChairBoarding = wheelChairBoarding
    }
}