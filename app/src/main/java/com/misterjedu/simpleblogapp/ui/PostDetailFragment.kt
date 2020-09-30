package com.misterjedu.simpleblogapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.modelFactory.FeedsVmFactory
import com.misterjedu.simpleblogapp.repository.Repository
import com.misterjedu.simpleblogapp.roomModel.Comment
import com.misterjedu.simpleblogapp.roomModel.DataBase
import com.misterjedu.simpleblogapp.roomModel.RoomDao
import com.misterjedu.simpleblogapp.ui.adapters.CommentRecyclerAdapter
import com.misterjedu.simpleblogapp.ui.dataclasses.PostObj
import com.misterjedu.simpleblogapp.viewmodel.CommentsFragmentVieModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_post_detail.*


class PostDetailFragment : Fragment() {

    private lateinit var posts: PostObj
    private lateinit var addCommentView: EditText
    private lateinit var viewModel: CommentsFragmentVieModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommentRecyclerAdapter
    private lateinit var roomDao: RoomDao
    private var postId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = arguments?.let { PostDetailFragmentArgs.fromBundle(it) }
        if (args != null) {
            posts = args.post!!
        }

        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Set Post Id
        postId = posts.postId.toInt()

        // Inflate the layout for this fragment
        recyclerView = comment_recycler_view

        //Get Reference to Edit TextView
        addCommentView = comment_edit_text_view

        //Set Value of all views
        detail_tag.text = posts.tag
        detail_author_name.text = posts.userName
        detail_date.text = posts.postedDate
        detail_post_body.text = posts.postBody

        //Get Author Profile Image
        Picasso.get()
            .load("https://picsum.photos/id/${posts.postId}/200/300")
            .into(detail_author_profile)

        //Get PostObj Background Image
        Picasso.get()
            .load("https://source.unsplash.com/collection/${posts.imageId}")
            .into(detail_header_background)

        //Instantiate Repository
        roomDao = DataBase.getPostDatabase(requireContext()).postDao()
        val repository = Repository(roomDao)
        val viewModelFactory = FeedsVmFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            CommentsFragmentVieModel::class.java
        )

        viewModel.getComments(posts.position.toString())
        viewModel.getRoomComments(postId)

        //initialize the PostObj recycler view adapter
        adapter = CommentRecyclerAdapter()
        recyclerView.adapter = adapter

        //Observer Comment Live Data Source
        viewModel.combinedCommentsData()?.observe(requireActivity(), { response ->
            response?.let {
                val combinedComments = response.second + response.first
                adapter.setComment(combinedComments)
            }
        })

        //Define Layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        //Set onclick listener on edit text to open the add comment fragment
        comment_edit_text_view.setOnClickListener {
            val action = PostDetailFragmentDirections
                .actionPostDetailFragmentToAddCommentFragment()
                .setPostId(posts.postId.toInt())
            it.findNavController().navigate(action)
        }


    }
}