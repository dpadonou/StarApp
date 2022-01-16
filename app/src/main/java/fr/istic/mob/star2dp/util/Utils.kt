package fr.istic.mob.star2dp.util

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import fr.istic.mob.star2dp.contract.StarContract
import fr.istic.mob.star2dp.models.BusRoutes
import fr.istic.mob.star2dp.models.Stops
import fr.istic.mob.star2dp.models.StopsTime

@SuppressLint("Range")
class Utils {
    /*var context: Context

    constructor(context: Context) {
        this.context = context
    }*/

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun setContext(context: Context) {
            this.context = context
        }

        /**
         * récupérer la liste des routes
         */
        fun getBusRoute(): List<BusRoutes> {
            val cursor: Cursor = this.context.contentResolver
                .query(StarContract.BusRoutes.CONTENT_URI, null, null, null, null)!!
            val busRoutes: MutableList<BusRoutes> = ArrayList()
            busRoutes.add(
                BusRoutes(
                    0,
                    "",
                    "Choisissez une ligne de bus",
                    "Choisissez une ligne de bus",
                    "",
                    "",
                    "E7E2E1",
                    "000000"
                )
            )
            while (cursor.moveToNext()) {
                val item = BusRoutes(
                    cursor.getInt(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.ROUTE_ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR))
                )
                busRoutes.add(item)
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
        fun searchStop(searchText: String): List<String> {
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
            val cursor: Cursor = this.context.contentResolver.query(
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
                        cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.STOP_ID)),
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
          /*  var lastIndex = listStops.size
            var stop_name = ""
            for (i in listStops.indices) {
                if (i == 0) {
                    stop_name = listStops[0].stopName
                }
                if (i != 0 && listStops[i].stopName == stop_name) {
                    lastIndex = i
                }
            }*/
            return listStops
        }

        /** Récupérer la liste des temps d'arrêts **/
        fun getStopTimes(data: Map<String, String>): List<StopsTime>? {
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
            val data: Map<String, String> = data
            val selectionArgs = arrayOf(data["trip_id"], data["hour"])
            val cursor: Cursor = this.context.contentResolver
                .query(StarContract.RouteDetails.CONTENT_URI, null, null, selectionArgs, null)!!
            val routeDetails: MutableList<RouteDetails> = ArrayList()

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
            busRoutes.add(BusRoutes(0, "", "", "", "", "", "", ""))
            while (cursor.moveToNext()) {
                val item = BusRoutes(
                    cursor.getInt(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns._ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.ROUTE_ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                    cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR))
                )
                busRoutes.add(item)
            }
            val i = busRoutes.size
            if (i != 0) {
                busRoutes.removeAt(i - 1)
            }
            cursor.close()
            return busRoutes
        }

        /** Ajouter # sur un string "**/
        fun appendHashtag(str: String): String {
            return "#$str"
        }

        fun getTerminus(str: String): List<Terminus> {
            var splitted = str.split("<>")
            var result: MutableList<Terminus> = mutableListOf()
            result.add(Terminus("", "Choisir une direction"))
            /* for (value: String in splitted) {
                 val firstSplit = value.split("(")
                 if (firstSplit.size > 1) {
                     val secondSplit = firstSplit[1].split(")")
                     result.add(Terminus("0", secondSplit[1]))
                     result.add(Terminus("1", secondSplit[0]))
                 }
             }*/
            for (i in splitted.indices) {
                if (i == 0) {
                    val firstSplit = splitted[i].split("(")
                    if (firstSplit.size > 1) {
                        val secondSplit = firstSplit[1].split(")")
                        result.add(Terminus("1", secondSplit[0]))
                    }
                } else {
                    val firstSplit = splitted[i].split("(")
                    if (firstSplit.size > 1) {
                        val secondSplit = firstSplit[1].split(")")
                        result.add(Terminus("0", secondSplit[0]))
                        if (firstSplit.size > 2) {
                            val secondSplit = firstSplit[2].split(")")
                            result.add(Terminus("0", secondSplit[0]))
                        }
                    }
                }
            }
            return result
        }

        fun sendData(data: HashMap<String, Any>): Bundle {
            val bundle = Bundle()
            bundle.putSerializable("data", data)

            return bundle
        }

        fun escapeQuotes(text: String): String {
            var cleared = text
            if(text.contains("\'")){
                cleared = text.replace("\'", "\\\'")
            }
            return cleared
        }
    }

}