package fr.istic.mob.star2dp.models

class BusRoutes {
    var _id:Int = 0
    var shortName: String = ""
    var longName: String = ""
    var description: String = ""
    var type: String = ""
    var color: String = ""
    var textColor: String = ""
    constructor(
        _id: Int,
        shortName: String,
        longName: String,
        description: String,
        type: String,
        color: String,
        textColor: String
    ) {
        this._id = _id
        this.shortName = shortName
        this.longName = longName
        this.description = description
        this.type = type
        this.color = color
        this.textColor = textColor
    }
}