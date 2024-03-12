package com.example.googlemapsfinprojongit

import android.util.Log
import com.example.googlemapsfinprojongit.dbmap.PlaceFB
import com.google.firebase.database.DatabaseReference

class PlaceRepository(private val database: DatabaseReference)  {
    //private val executor = Executors.newSingleThreadExecutor()
    fun add(title:String, location:String, urlImage:String):Boolean {
        var ret = true
        val placeId = database.push().key
        if (placeId == null) {
            ret = false
        } else {
            val placeNew = PlaceFB(id = placeId, title = title, location = location, urlImage = urlImage)
            database.child(placeNew.id).setValue(placeNew).addOnCompleteListener {
                if(!it.isSuccessful) ret = false
            }
        }
        return ret
    }

    fun remove (place: PlaceFB) {
        val userId = place.id
        database.get().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                task.result.children.forEach {
                    val newItem = it.key
                    if ( newItem != null && userId == newItem) {
                        database.child(place.id).removeValue().addOnSuccessListener {
                            Log.d(
                                "MYRES2",
                                "Field with id = $userId removed!!!"
                            )
                            return@addOnSuccessListener
                        }
                    }
                }
            }
        }
    }
}