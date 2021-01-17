package com.example.socialnetwork.ui.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.socialnetwork.R
import com.example.socialnetwork.data.Post
import com.example.socialnetwork.ui.comments.CommentsActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_post_get.*

class GetPostFragment: Fragment(R.layout.fragment_post_get) {
    private val db = FirebaseFirestore.getInstance()
    private val postAdapter = PostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllPost()

        recView.adapter = postAdapter
        recView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        postAdapter.setOnCommentClickListener {
            val intent = Intent(requireContext(), CommentsActivity::class.java)
            intent.putExtra("postId", it.id)
            startActivity(intent)
        }
    }

    private fun getAllPost() {
        val result: MutableList<Post> = mutableListOf()
        db.collection("posts").addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            result.clear()
            db.collection("posts").get()
                .addOnSuccessListener {
                    it.documents.forEach { doc ->
                        val model = doc.toObject(Post::class.java)
                        model?.id = doc.id
                        model?.let { model->
                            result.add(model)
                        }
                        postAdapter.models = result
                        Log.d("maglumat", result.toString())
                    }
            }
        }
    }
}