package com.example.socialnetwork.ui.comments.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.socialnetwork.R
import com.example.socialnetwork.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_comment.*
import kotlinx.android.synthetic.main.activity_comments.*

class AddCommentActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val mAuth = FirebaseAuth.getInstance()
    var postId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)
        postId = intent.getStringExtra("postId") ?: ""

        btSend.setOnClickListener {
            if (!etComment.text.isNullOrEmpty()) {
                addCommentLoading.visibility = View.VISIBLE

                db.collection("posts").document(postId).get().addOnSuccessListener { doc->
                    if (doc.exists()) {
                        val post = doc.toObject(Post::class.java)

                        var username: String = ""
                        db.collection("users").document(mAuth.currentUser!!.uid).get()
                                .addOnSuccessListener { user->
                                    username = user.get("username").toString()

                                    post?.comments?.add(mapOf("username" to username, "comment_text" to etComment.text.toString()))

                                    var map: MutableMap<String, Any?> = mutableMapOf()
                                    map["id"] = postId
                                    map["theme"] = post?.theme
                                    map["text"] = post?.text
                                    map["like"] = post?.like
                                    map["dislike"] = post?.dislike
                                    map["username"] = post?.username
                                    map["userId"] = post?.userId
                                    map["commments"] = post?.comments

                                    db.collection("posts").document(postId).set(map)
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "Your comment added", Toast.LENGTH_SHORT).show()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(this, "Your comment not added", Toast.LENGTH_SHORT).show()
                                            }
                                            .addOnCompleteListener {
                                                addCommentLoading.visibility = View.GONE
                                                etComment.text.clear()
                                                finish()
                                            }
                                }
                                }


                }

                addCommentLoading.visibility = View.GONE
            }

        }
    }
}