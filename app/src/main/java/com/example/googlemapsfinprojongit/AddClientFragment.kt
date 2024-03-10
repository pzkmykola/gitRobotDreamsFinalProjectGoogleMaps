package com.example.googlemapsfinprojongit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class AddClientFragment :Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_client_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputField: EditText = view.findViewById(R.id.clientName)
        val button: Button = view.findViewById(R.id.addButton)
        val database = FirebaseDatabase.getInstance(
            "https://mapsfinprojongit-default-rtdb.europe-west1.firebasedatabase.app/"
        )
        val account =
            GoogleSignIn.getLastSignedInAccount(requireContext())
        button.setOnClickListener {
            val target = database.reference.child(
                account?.id ?: "unknown_account"
            )
                .child("clients")
                .child(UUID.randomUUID().toString())
            target.setValue(
                inputField.text.toString()
            ).addOnCompleteListener {
                parentFragmentManager.popBackStack()
            }
        }
    }
}