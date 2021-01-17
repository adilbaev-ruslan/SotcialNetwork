package com.example.socialnetwork.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import com.example.socialnetwork.data.Post
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    var models: List<Post> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun populateModel(model: Post){
            itemView.tvTheme.text = model.theme
            itemView.tvUsername.text = model.username
            itemView.tvText.text = model.text
            itemView.ivComments.setOnClickListener {
                onCommentClicked.invoke(model)
            }
        }
    }

    private var onCommentClicked: (model: Post) -> Unit  = {}

    fun setOnCommentClickListener(onCommentClicked: (model: Post) -> Unit) {
        this.onCommentClicked = onCommentClicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size
}