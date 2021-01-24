package com.example.socialnetwork.ui.comments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialnetwork.R
import com.example.socialnetwork.ui.comments.add.AddCommentActivity
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
        rvComments.adapter = commentsAdapter
        showComments(postId)

        addCommentButton.setOnClickListener {
            val intent = Intent(this, AddCommentActivity::class.java)
            intent.putExtra("postId", postId)
            startActivity(intent)
        }
    }

    private fun showComments(postId: String) {
        db.collection("posts").document(postId).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    it.get("comments")?.let { comments ->
                        commentsAdapter.models = comments as List<Map<String, String>>
                    }
                }
            }
    }
}
