package com.example.socialnetwork.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialnetwork.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db.collection("users").document(mAuth.currentUser?.uid!!.toString()).get()
            .addOnCompleteListener {
                Log.d("tekseriw", it.result.toString())
                if (it.isSuccessful && !it.result?.exists()!!) {
                    val map: MutableMap<String, Any?> = mutableMapOf()
                    map["email"] = mAuth.currentUser?.email
                    db.collection("users").document(mAuth.currentUser?.uid!!).set(map)
                        .addOnSuccessListener {
                            Log.d("user", "User has been added successfully")
                        }
                        .addOnFailureListener {e->
                            Log.d("user", e.localizedMessage!!.toString())
                        }
                }
            }
    }
}