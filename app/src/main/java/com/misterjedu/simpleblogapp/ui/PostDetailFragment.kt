package com.misterjedu.simpleblogapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.model.RetroComment
import com.misterjedu.simpleblogapp.modelFactory.FeedsVmFactory
import com.misterjedu.simpleblogapp.repository.Repository
import com.misterjedu.simpleblogapp.ui.adapters.CommentRecyclerAdapter
import com.misterjedu.simpleblogapp.ui.dataclasses.Post
import com.misterjedu.simpleblogapp.viewmodel.CommentsFragmentVieModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_feeds.*
import kotlinx.android.synthetic.main.fragment_post_detail.*


class PostDetailFragment : Fragment() {

    private lateinit var posts: Post
    private lateinit var viewModel: CommentsFragmentVieModel
    private var adapter = CommentRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Get bundle items
        val bundle: Bundle? = this.arguments
        if (bundle !== null) {
            posts = bundle.getParcelable("posts")!!
        }

        //Set Value of all views
        detail_tag.text = posts.tag
        detail_author_name.text = posts.userName
        detail_date.text = posts.postedDate
        detail_post_body.text = posts.postBody

        //Get Author Profile Image
        Picasso.get()
            .load("https://picsum.photos/id/${posts.postId}/200/300")
            .into(detail_author_profile)

        //Get Post Background Image
        Picasso.get()
            .load("https://source.unsplash.com/collection/${posts.imageId}")
            .into(detail_header_background)


        //Instantiate Repository
        val repository = Repository()
        val viewModelFactory = FeedsVmFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            CommentsFragmentVieModel::class.java
        )

        viewModel.getComments(posts.position.toString())

        //initialize the Post recycler view adapter
        comment_recycler_view.adapter = adapter

        //Observer Comment Live Data Source

        viewModel.allComments.observe(requireActivity(), { response ->
            if (response.isSuccessful) {
                response.body()?.let { it -> adapter.setComment(it) }
                comment_recycler_view.layoutManager = LinearLayoutManager(requireContext())
            } else {
                Log.i("Response", response.errorBody().toString())
            }
        })

    }
}