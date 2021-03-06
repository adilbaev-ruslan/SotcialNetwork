package com.example.socialnetwork.ui.add

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.socialnetwork.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_post_add.*
import kotlinx.android.synthetic.main.fragment_profile.*

class AddPostFragment: Fragment(R.layout.fragment_post_add) {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var username = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData()
        postSaveButton.setOnClickListener {
            showPostLoading(true)
            val map: MutableMap<String, Any> = mutableMapOf()
            map["theme"] = etTheme.text.toString()
            map["text"] = etText.text.toString()
            map["userId"] = mAuth.currentUser?.uid!!
            map["username"] = username
            map["like"] = 0
            map["dislike"] = 0
            map["comments"] = arrayListOf<String>()
            db.collection("posts").document().set(map)
                    .addOnSuccessListener {
                        showPostLoading(false)
                        Toast.makeText(requireContext(), "Your post is published", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        showPostLoading(false)
                        Toast.makeText(requireContext(), e.localizedMessage.toString(), Toast.LENGTH_LONG).show()
                    }
        }
    }

    private fun showData() {
        db.collection("users").document(mAuth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    username = it.get("username").toString()
                }
    }

    private fun showPostLoading(isLoading: Boolean) {
        postLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        etTheme.isEnabled = !isLoading
        etText.isEnabled = !isLoading
        postSaveButton.isEnabled = !isLoading
    }
}