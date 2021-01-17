package com.example.socialnetwork.ui.comments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.socialnetwork.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val commentsAdapter = CommentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        var postId = ""
        postId = intent.getStringExtra("postId") ?: ""
        showComments(postId)
        rvComments.adapter = commentsAdapter
        rvComments.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    private fun showComments(postId: String) {
        db.collection("posts").document(postId).get().addOnSuccessListener {
            if (it.exists()) {
                it.get("comments")?.let { comments->
                    commentsAdapter.models = comments as List<Map<String, String>>
                }
            }
        }
    }
}