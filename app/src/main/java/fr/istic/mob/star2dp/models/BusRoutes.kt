package fr.istic.mob.star2dp.models

import fr.istic.mob.star2dp.util.Utils
import kotlin.reflect.typeOf

class BusRoutes {
    var _id:Int = 0
    var shortName: String = ""
    var routeId:String = ""
    var longName: String = ""
    var description: String = ""
    var type: String = ""
    var color: String = ""
    var textColor: String = ""
    constructor(
        _id: Int,
        routeId:String,
        shortName: String,
        longName: String,
        description: String,
        type: String,
        color: String,
        textColor: String
    ) {
        this._id = _id
        this.routeId = routeId
        this.shortName = shortName
        this.longName = longName
        this.description = description
        this.type = type
        this.color = color
        this.textColor = textColor
    }

    fun hasSameId(busRoutes: BusRoutes): Boolean{
        return this._id == busRoutes._id
    }

    override fun toString(): String {
        return shortName
    }
}