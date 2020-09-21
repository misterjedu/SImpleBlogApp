package com.misterjedu.simpleblogapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.helpers.DateGenerator
import com.misterjedu.simpleblogapp.helpers.NameGenerator
import com.misterjedu.simpleblogapp.helpers.ReadTimeGenerator
import com.misterjedu.simpleblogapp.helpers.TagGenerator
import com.misterjedu.simpleblogapp.model.RetroPost
import com.misterjedu.simpleblogapp.ui.dataclasses.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_post.view.*
import java.util.*

class PostRecyclerAdapter(
    private val clickListener: OnResultClickListener
) : RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {
    private var postList = mutableListOf<RetroPost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val postView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_post, parent, false)
        return PostViewHolder(postView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        //Get current Post from the Array
        holder.initialize(postList[position], clickListener)
    }

    override fun getItemCount() = postList.size


    //Comment ViewHolder Class
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Get reference to the views
        private var postAuthorImage: ImageView = itemView.post_author_image
        private var postAuthorName: TextView = itemView.post_author_name
        private var postDate: TextView = itemView.post_date
        private var postSummary: TextView = itemView.post_summary
        private var postTag: TextView = itemView.post_tag
        private var postImage: ImageView = itemView.post_image
        private var readTime: TextView = itemView.post_read_time

        fun initialize(item: RetroPost, action: OnResultClickListener) {

            //Generate names for the fields
            val userName = NameGenerator.getName()
            val postedDate = DateGenerator.getPostDate()
            val tag = TagGenerator.getTag()
            val readingTime = ReadTimeGenerator.getTime()
            val imageId = (Math.random() * 500).toInt()

            //Set Post views
            postAuthorName.text = userName
            postDate.text = postedDate
            postSummary.text = item.title.capitalize(Locale.ROOT)
            postTag.text = tag
            readTime.text = readingTime

            //Load Author Image from randomuser.me
            Picasso.get()
                .load("https://randomuser.me/api/portraits/men/${adapterPosition}.jpg")
                .into(postAuthorImage)

            //Load Author Image from randomuser.me
            Picasso.get()
                .load("https://source.unsplash.com/collection/$imageId")
                .into(postImage)

            //Post Item to be passed from the Post Fragment to the Post Detail Fragment via Bundle
            val postItem = Post(
                userName, postedDate, tag, readingTime,
                imageId.toString(), adapterPosition,
                item.title, item.body, item.id.toString()
            )

            //Onclick Listener for the interface
            itemView.setOnClickListener {
                action.onItemClick(postItem)
            }

        }
    }

    //Method to set the array of posts
    fun setPosts(post: List<RetroPost>) {
        this.postList = post as MutableList<RetroPost>
        notifyDataSetChanged()
    }


    //OnClick Listener InterfaceR
    interface OnResultClickListener {
        fun onItemClick(item: Post)
    }

}