package com.misterjedu.simpleblogapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.databinding.SingleCommentBinding
import com.misterjedu.simpleblogapp.helpers.DateGenerator
import com.misterjedu.simpleblogapp.roomModel.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_comment.view.*

class CommentRecyclerAdapter() :
    RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>() {

    //ArrayList of comments
    private var comments = listOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = SingleCommentBinding.inflate(layoutInflater, parent, false)

        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.initialize(comments[position])
    }

    //Size of comment array.
    override fun getItemCount() = comments.size


    //Call this method to pass in an array of comments
    fun setComment(comments: List<Comment>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    //Comment ViewHolder Class
    class CommentViewHolder(private val binding: SingleCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var commentAuthorImage = binding.commentProfileImage
        private val postedDate = DateGenerator.getPostDate()

        //Bind View to data
        fun initialize(item: Comment) {
            binding.commentAuthorName.text = item.name
            binding.commentDate.text = item.body
            binding.commentDate.text = postedDate

            //Fetch Random User Image from randomuser.me using Picasso
            Picasso.get()
                .load("https://picsum.photos/id/${item.postId}/200/300")
                .into(commentAuthorImage)
        }
    }
}