package com.misterjedu.simpleblogapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.ui.dataclasses.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_comment.view.*

class CommentRecyclerAdapter(private val clickListener: OnResultClickListener) :
    RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>() {

    //ArrayList of comments
    private var comments = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val commentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_comment, parent, false)

        return CommentViewHolder(commentView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.initialize(comments[position], clickListener)
    }

    //Size of comment array.
    override fun getItemCount() = comments.size


    //Call this method to pass in an array of comments
    fun setComment(comments: MutableList<Comment>) {
        this.comments = comments as MutableList<Comment>
        notifyDataSetChanged()
    }

    //Comment ViewHolder Class
    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var commentAuthorImage: ImageView = itemView.comment_profile_image
        private var commentAuthorName: TextView = itemView.comment_author_name
        private var commentDate: TextView = itemView.comment_date
        private var commentBody: TextView = itemView.comment_body

        fun initialize(item: Comment, action: OnResultClickListener) {
            commentAuthorName.text = item.username
            commentBody.text = item.post
            commentDate.text = item.date

            //Fetch Random User Image from randomuser.me using Picasso
            Picasso.get()
                .load("https://randomuser.me/api/portraits/men/${item.id}.jpg")
                .into(commentAuthorImage)
        }
    }


    //OnClick Listener InterfaceR
    interface OnResultClickListener {
        fun onItemClick(item: Comment, position: Int)
    }
}