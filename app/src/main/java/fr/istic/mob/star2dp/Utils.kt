package fr.istic.mob.star2dp

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import fr.istic.mob.star2dp.models.*
import fr.istic.mob.star2dp.models.StarContract
@SuppressLint("Range")
class Utils {
    var context:Context

    constructor(context: Context) {
        this.context = context
    }

    /**
     * récupérer la liste des routes
     */
    fun getBusRoute(): List<BusRoutes>? {
        val cursor: Cursor = this.context.contentResolver
            .query(StarContract.BusRoutes.CONTENT_URI, null, null, null, null)!!
        val busRoutes: MutableList<BusRoutes> = ArrayList()
        busRoutes.add(BusRoutes(0, "", "", "", "", "", ""))
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val item = BusRoutes(
                    cursor.getInt(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR))
                )
                busRoutes.add(item)
            }
        }
        val i = busRoutes.size
        if (i != 0) {
            busRoutes.removeAt(i - 1)
        }
        cursor.close()
        return busRoutes
    }

    /**
     * Récupérer la liste des arrêts correspondant à la recherche
     */

    fun searchStop(searchText: String): List<String>? {
        var searchText = searchText
       searchText = searchText.trim { it <= ' ' }
        val listStops: MutableList<String> = ArrayList()
        if (searchText != "") {
            val selectionArgs = arrayOf(searchText)
            val cursor: Cursor? = this.context.contentResolver.query(
                StarContract.SearchedStops.CONTENT_URI,
                null,
                null,
                selectionArgs,
                StarContract.Stops.StopColumns._ID
            )
            if (cursor!!.moveToFirst()) {
                do {
                    listStops.add(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)))
                } while (cursor.moveToNext())
            }
        }
        return listStops
    }

    /**
     * Récuperer la liste des arrêts en fonction de la direction et de la route
     */
    fun getStop(busRouteId: String, directionId: String): List<Stops>? {
        val listStops: MutableList<Stops> = ArrayList()
        val selectionArgs = arrayOf(busRouteId, directionId)
        val cursor: Cursor = this.context.getContentResolver().query(
            StarContract.Stops.CONTENT_URI,
            null,
            null,
            selectionArgs,
            StarContract.Stops.StopColumns._ID
        )!!
        if (cursor.moveToFirst()) {
            do {
                val item = Stops(
                    cursor.getInt(cursor.getColumnIndex(StarContract.Stops.StopColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.LONGITUDE)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING))
                )
                listStops.add(item)
                //idtrips.add(cursor.getString(cursor.getColumnIndex(StarContract.Trips.TripColumns.TRIP_ID))) ;
            } while (cursor.moveToNext())
        }
        /**
         * Eliminate duplicates
         */
        var lastIndex = listStops.size
        var stop_name = ""
        for (i in listStops.indices) {
            if (i == 0) {
                stop_name = listStops[0].stopName
            }
            if (i != 0 && listStops[i].stopName.equals(stop_name)) {
                lastIndex = i
            }
        }
        return listStops.subList(0, lastIndex)
    }

    /** Récupérer la liste des temps d'arrêts **/
    fun getStopTimes( data: Map<String, String>): List<StopsTime>? {
        val data: Map<String, String> = data

        // Log.i("TEST","route_id : "+data.get("route_id")+", stop_id : "+data.get("stop_id")+", direction_id : "+data.get("direction_id")+", day : "+data.get("day")+", arrival_time : "+data.get("arrival_time") ) ;
        val selectionArgs = arrayOf(
            data["route_id"],
            data["stop_id"], data["direction_id"], data["day"], data["arrival_time"]
        )
        val cursor: Cursor = this.context.contentResolver.query(
            StarContract.StopTimes.CONTENT_URI,
            null,
            null,
            selectionArgs,
            StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME
        )!!
        val stopTimes: MutableList<StopsTime> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val item = StopsTime(
                        0,
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.TRIP_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE))
                    )
                    stopTimes.add(item)
                } while (cursor.moveToNext())
            }
        }
        return stopTimes
    }

    /**
     * Récupérer la liste des détails de route(nom et temps d'arrivée )
     */
    fun getRouteDetails(data: Map<String, String>): List<RouteDetails>? {
        val data: Map<String, String> =data
        val selectionArgs = arrayOf(data["trip_id"], data["hour"])
        val cursor: Cursor = this.context.contentResolver
            .query(StarContract.RouteDetails.CONTENT_URI, null, null, selectionArgs, null)!!
        val routeDetails: MutableList<RouteDetails> = ArrayList()
        if (cursor != null) {
        }
        if (cursor.moveToFirst()) {
            do {
                val item = RouteDetails(
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME))
                )
                routeDetails.add(item)
            } while (cursor.moveToNext())
        }
        return routeDetails
    }

    /**
     * récupérer la liste des trajets passant par une route
     */
    fun getBusRoute(stop_name: String): List<BusRoutes>? {
        val selectionArgs = arrayOf(stop_name)
        val cursor: Cursor = this.context.contentResolver.query(
            StarContract.RoutesForStop.CONTENT_URI,
            null,
            null,
            selectionArgs,
            StarContract.Stops.StopColumns._ID
        )!!
        val busRoutes: MutableList<BusRoutes> = ArrayList()
        busRoutes.add(BusRoutes(0,"", "", "", "", "", ""))
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val item = BusRoutes(
                    cursor.getInt(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR))
                )
                busRoutes.add(item)
            }
        }
        val i = busRoutes.size
        if (i != 0) {
            busRoutes.removeAt(i - 1)
        }
        cursor.close()
        return busRoutes
    }

}