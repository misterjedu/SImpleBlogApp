package com.misterjedu.simpleblogapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.ui.dataclasses.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_post.view.*

class PostRecyclerAdapter : RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {
    private var postList = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val postView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_post, parent, false)
        return PostViewHolder(postView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        //Get current Post from the Array
        val currentPost = postList[position]

        //Set Post views
        holder.postAuthorName.text = currentPost.username
        holder.postDate.text = currentPost.date
        holder.postSummary.text = currentPost.post
        holder.postTag.text = currentPost.tag


        //Load Author Image from randomuser.me
        Picasso.get()
            .load("https://randomuser.me/api/portraits/men/${currentPost.id}.jpg")
            .into(holder.postAuthorImage)

        //Load Author Image from randomuser.me
        Picasso.get()
            .load("https://source.unsplash.com/700x700/?nature,technology")
            .into(holder.postImage)
    }

    override fun getItemCount() = postList.size


    //Comment ViewHolder Class
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postAuthorImage: ImageView = itemView.post_author_image
        var postAuthorName: TextView = itemView.post_author_name
        var postDate: TextView = itemView.post_date
        var postSummary: TextView = itemView.post_summary
        var postTag: TextView = itemView.post_tag
        var postImage: ImageView = itemView.post_image
    }
}