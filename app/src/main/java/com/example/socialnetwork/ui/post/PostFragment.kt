package com.example.socialnetwork.ui.post

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.socialnetwork.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment: Fragment(R.layout.fragment_post) {

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postSaveButton.setOnClickListener {
            showPostLoading(true)
            val map: MutableMap<String, Any> = mutableMapOf()
            map["text"] = etText.text.toString()
            map["userId"] = mAuth.currentUser?.uid!!
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

    private fun showPostLoading(isLoading: Boolean) {
        postLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        etText.isEnabled = !isLoading
        postSaveButton.isEnabled = !isLoading
    }
}