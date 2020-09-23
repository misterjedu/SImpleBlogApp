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
    private lateinit var addCommentButton: ImageView
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
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Get bundle items
        val bundle: Bundle? = this.arguments
        if (bundle !== null) {
            posts = bundle.getParcelable("posts")!!
        }


//        adapter = CommentRecyclerAdapter()

        // Inflate the layout for this fragment
        recyclerView = comment_recycler_view

        //Get Reference to Edit TextView and Send Add Comment Button
        addCommentView = comment_edit_text_view
        addCommentButton = save_comment_button

        //Set Value of all views
        detail_tag.text = posts.tag
        detail_author_name.text = posts.userName
        detail_date.text = posts.postedDate
        detail_post_body.text = posts.postBody

        //Set Value of Post postId
        postId = posts.postId.toInt()

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

        //Get Comments from Api and Room
        viewModel.getComments(posts.position.toString())
        viewModel.getRoomComments(postId)

        //Add Comment to Rppm Database
        addCommentButton.setOnClickListener {
            val commentAdded: String = addCommentView.text.toString()
            val comment = Comment(
                postId, 0, "Mister Jedu",
                "misterjedu.com", commentAdded
            )
            viewModel.addComment(comment)

            addCommentView.clearFocus()
            addCommentView.setText("")
            Toast.makeText(requireContext(), "Comment Added", Toast.LENGTH_SHORT).show()
        }


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

    }
}