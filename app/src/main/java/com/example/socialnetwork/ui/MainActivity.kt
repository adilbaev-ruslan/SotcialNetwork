package com.example.socialnetwork.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.socialnetwork.R
import com.example.socialnetwork.ui.add.AddPostFragment
import com.example.socialnetwork.ui.post.GetPostFragment
import com.example.socialnetwork.ui.profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

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

            bnav.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.itemProfil -> {
                        val fragment = ProfileFragment()
                        changeFragment(fragment, R.id.fragmentContext)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.itemAdd -> {
                        val fragment = AddPostFragment()
                        changeFragment(fragment, R.id.fragmentContext)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.itemHome -> {
                        val fragment = GetPostFragment()
                        changeFragment(fragment, R.id.fragmentContext)
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> return@setOnNavigationItemSelectedListener false
                }
            }

    }

    private fun changeFragment(fragment: Fragment, contener: Int) {
        supportFragmentManager.beginTransaction().replace(contener, fragment).commit()
    }
}