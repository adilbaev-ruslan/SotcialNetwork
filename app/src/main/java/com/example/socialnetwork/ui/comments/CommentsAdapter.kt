package com.example.socialnetwork.ui.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    var models: List<Map<String, String>> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CommentsViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        fun populateModel(comment: Map<String, String>) {
            itemView.tvComment.text = comment["comment_text"]
            itemView.tvUsername.text = "@${comment["username"]}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size
}