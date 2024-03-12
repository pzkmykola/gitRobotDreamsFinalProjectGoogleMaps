package com.example.googlemapsfinprojongit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemapsfinprojongit.dbmap.PlaceFB
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeListFragment: Fragment() {
    private lateinit var listView: RecyclerView
    private lateinit var adapter: PlaceListAdapter
    private lateinit var viewModel: PlaceViewModel
    private var account: GoogleSignInAccount? = null
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(
        "https://mapsfinprojongit-default-rtdb.europe-west1.firebasedatabase.app/"
    )
    //private var target: DatabaseReference? = null
    private val target = MyApplication.getApp().target
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val fabRunMap = view.findViewById<FloatingActionButton>(R.id.fabMap)
        listView = view.findViewById(R.id.list)
        listView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PlaceListAdapter()
        listView.adapter = adapter
//        account = GoogleSignIn.getLastSignedInAccount(requireContext())
//        target  =  database.reference
//            .child(account?.id ?: "unknown_account").child("Places")
        fab.setOnClickListener {
            val activity = requireActivity() as OnAddClickListener
            activity.onFabClick()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, AddPlaceFragment())
                .commit()
        }

        target.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val placeList = mutableListOf<PlaceFB>()
                if (snapshot.exists()) {
                    snapshot.children.forEach {
                        val taskKey: String = it.key!!
                        if (taskKey != "") {
                            val newItem = it.getValue(PlaceFB::class.java)
                            if (newItem != null && taskKey == newItem.id) {
                                Log.d(
                                    "MYRES1",
                                    "${newItem.id}/${newItem.title}/${newItem.location}/${newItem.urlImage}"
                                )
                                placeList.add(newItem)
                            }
                        }
                    }
                    adapter = PlaceListAdapter(placeList)
                    listView.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        fabRunMap.setOnClickListener {
            val intent = Intent(context, MapsActivity::class.java)
            startActivity(intent)
            parentFragmentManager.beginTransaction()
                .add(com.google.android.material.R.id.container, SupportMapFragment())
                .addToBackStack("mapFragment")
                .commit()
        }
    }
}