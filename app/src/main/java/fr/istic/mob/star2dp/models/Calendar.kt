package fr.istic.mob.star2dp.models

class Calendar {
    var id:Int = 0
    var serviceId:String = ""
    var monday:String = ""
    var tuesday:String = ""
    var wednesday = ""
    var thursday:String = ""
    var friday:String = ""
    var saturday:String = ""
    var sunday:String = ""
    var startDate:String = ""
    var endDate = ""

    constructor(
        id: Int,
        serviceId:String,
        monday: String,
        tuesday: String,
        wednesday: String,
        thursday: String,
        friday: String,
        saturday: String,
        sunday: String,
        startDate: String,
        endDate: String
    ) {
        this.id = id
        this.serviceId = serviceId
        this.monday = monday
        this.tuesday = tuesday
        this.wednesday = wednesday
        this.thursday = thursday
        this.friday = friday
        this.saturday = saturday
        this.sunday = sunday
        this.startDate = startDate
        this.endDate = endDate
    }

}