package com.example.googlemapsfinprojongit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googlemapsfinprojongit.Client.client
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//, OnAuthLaunch, OnAddClickListener
class MainActivity : AppCompatActivity(), OnAuthLaunch, OnAddClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun launch(intent: Intent) {
        startActivityForResult(intent, 1)
    }

    override fun showListFragment() {
        supportFragmentManager.beginTransaction()
            .add(com.google.android.material.R.id.container, HomeListFragment())
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val result = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(result.idToken, null)
                val auth = FirebaseAuth.getInstance()
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showListFragment()
                        } else {
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException) {
                Toast.makeText(this, "Error $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onFabClick() {
        supportFragmentManager.beginTransaction()
            .add(com.google.android.material.R.id.container, AddClientFragment())
            .addToBackStack("clientFragment")
            .commit()
    }
}
interface OnAuthLaunch {
    fun launch(intent: Intent)
    fun showListFragment()
}

interface OnAddClickListener{
    fun onFabClick()
}

//    val supportMapFragment =
//        supportFragmentManager.findFragmentById(R.id.container) as SupportMapFragment
//        supportMapFragment.getMapAsync { map ->
//            val coordinatesOfLviv = LatLng(49.842957, 24.031111)
//            map.addMarker(MarkerOptions().position(coordinatesOfLviv).title("My Position"))
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinatesOfLviv, 8F))
//            CoroutineScope(Dispatchers.IO).launch {
//                val result = client.create(ApiInterface::class.java).getSimpleRoute()
//                if (result.isSuccessful){
//                    withContext(Dispatchers.Main) {
//                        result.body()?.let {
//                            val polylinePoints = it.routes[0].overviewPolyline.points
//                            val decodedPath = PolyUtil.decode(polylinePoints)
//                            map.addPolyline(PolylineOptions().addAll(decodedPath))
//                        }
//                    }
//                }
//            }
//        }
//    }
//}