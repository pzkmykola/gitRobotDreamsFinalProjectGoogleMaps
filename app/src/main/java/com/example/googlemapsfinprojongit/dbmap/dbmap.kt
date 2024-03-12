package com.example.googlemapsfinprojongit.dbmap

import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.google.gson.annotations.SerializedName

data class PlaceFB (
    var id: String = "",     // This is the id of location in the database
    var title: String = "",  // This is the title of location
    var location: String = "",   // (21.1, 42.2) values as strings
    var urlImage: String = ""    // 2021-01-01
)

data class DirectionsResponse(val routes:List<Routes>)
data class Routes(@SerializedName("overview_polyline" ) val overviewPolyline:OverviewPolyline)
data class OverviewPolyline(val points:String)

interface PlaceDao {
    //@Insert
    fun add(title: String, location: String, urlImage: String):Boolean
    //@Delete
    fun remove(place: PlaceFB)
}

abstract class LocationDatabase : RoomDatabase(){
    abstract fun placeDao():PlaceDao
}