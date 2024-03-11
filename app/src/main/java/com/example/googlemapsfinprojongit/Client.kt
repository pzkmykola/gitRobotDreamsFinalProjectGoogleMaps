 package com.example.googlemapsfinprojongit

import com.google.android.gms.common.api.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Client {
    val client: Retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface ApiInterface {
    @GET("/maps/api/directions/json?&origin=49.842957,24.031111&destination=49.553516,25.594767&key=AIzaSyBM36Lb3FAJumpAbWm1PBG50RA-VTwpWHk")
    suspend fun getSimpleRoute(): retrofit2.Response<DirectionsResponse>
}