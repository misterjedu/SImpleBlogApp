package com.misterjedu.simpleblogapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.databinding.SinglePostBinding
import com.misterjedu.simpleblogapp.helpers.DateGenerator
import com.misterjedu.simpleblogapp.helpers.NameGenerator
import com.misterjedu.simpleblogapp.helpers.ReadTimeGenerator
import com.misterjedu.simpleblogapp.helpers.TagGenerator
import com.misterjedu.simpleblogapp.roomModel.Post
import com.misterjedu.simpleblogapp.ui.dataclasses.PostObj
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_post.view.*
import java.util.*

class PostRecyclerAdapter(
    private val clickListener: OnResultClickListener
) : RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {
    private var postList = mutableListOf<Post>()

    //Bind Single Post view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SinglePostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        //Get current PostObj from the Array
        holder.initialize(postList[position], clickListener)
    }

    override fun getItemCount() = postList.size


    //Comment ViewHolder Class
//    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class PostViewHolder(private val binding: SinglePostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Get reference to the views
        private var postAuthorImage: ImageView = binding.postAuthorImage
        private var postImage: ImageView = binding.postImage

        //Bind View to data
        fun initialize(item: Post, action: OnResultClickListener) {

            //Generate names for the fields
            val userName = NameGenerator.getName()
            val postedDate = DateGenerator.getPostDate()
            val tag = TagGenerator.getTag()
            val readingTime = ReadTimeGenerator.getTime()
            val imageId = (Math.random() * 500).toInt()

            //Set PostObj views
            binding.postAuthorName.text = userName
            binding.postDate.text = userName
            binding.postSummary.text = item.title.capitalize(Locale.ROOT)
            binding.postTag.text = tag
            binding.postReadTime.text = readingTime

            //Load Author Image from randomuser.me
            Picasso.get()
                .load("https://randomuser.me/api/portraits/men/${adapterPosition}.jpg")
                .into(postAuthorImage)

            //Load Author Image from randomuser.me
            Picasso.get()
                .load("https://source.unsplash.com/collection/$imageId")
                .into(postImage)

            //PostObj Item to be passed from the Post Fragment to the Details Fragment via Bundle
            val postItem = PostObj(
                userName, postedDate, tag, readingTime,
                imageId.toString(), adapterPosition,
                item.title, item.body, item.id.toString()
            )

            //Onclick Listener for the interface
            itemView.setOnClickListener {
                action.onItemClick(it, postItem)
            }

        }
    }

    //Method to set the array of posts
    fun setPosts(post: List<Post>) {
        this.postList = post as MutableList<Post>
        notifyItemInserted(0)
    }

    //OnClick Listener InterfaceR
    interface OnResultClickListener {
        fun onItemClick(view: View, item: PostObj)
    }

}