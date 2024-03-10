package com.example.googlemapsfinprojongit

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(val routes:List<Routes>)
data class Routes(@SerializedName("overview_polyline" ) val overviewPolyline:OverviewPolyline)
data class OverviewPolyline(val points:String)