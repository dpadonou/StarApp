package fr.istic.mob.star2dp.util

class Terminus {
    var id:String=""
    var name:String = ""

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return name
    }
}
