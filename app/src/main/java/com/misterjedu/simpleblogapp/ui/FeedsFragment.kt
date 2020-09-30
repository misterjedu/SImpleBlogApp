package com.misterjedu.simpleblogapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.databinding.FragmentFeedsBinding
import com.misterjedu.simpleblogapp.modelFactory.FeedsVmFactory
import com.misterjedu.simpleblogapp.repository.IRepository
import com.misterjedu.simpleblogapp.repository.Repository
import com.misterjedu.simpleblogapp.roomModel.Post
import com.misterjedu.simpleblogapp.roomModel.RoomDao
import com.misterjedu.simpleblogapp.roomModel.DataBase
import com.misterjedu.simpleblogapp.ui.adapters.PostRecyclerAdapter
import com.misterjedu.simpleblogapp.ui.dataclasses.PostObj
import com.misterjedu.simpleblogapp.ui.dialogs.AddNewPostDialog
import com.misterjedu.simpleblogapp.viewmodel.FeedsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_feeds.*

class FeedsFragment : Fragment(), PostRecyclerAdapter.OnResultClickListener {

    private lateinit var addPostFab: FloatingActionButton
    private lateinit var newPostDialog: AddNewPostDialog
    private lateinit var viewModel: FeedsFragmentViewModel
    private var adapter = PostRecyclerAdapter(this)
    private var isFragmentVisible = true
    private var isConnection = true
    private lateinit var repository: IRepository
    private lateinit var roomDao: RoomDao
    private lateinit var binding: FragmentFeedsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment with binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feeds, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //If the fragment is in view, set fragment visibility to true
        isFragmentVisible = true

        //Instantiate Repository
        roomDao = DataBase.getPostDatabase(requireContext()).postDao()
        repository = Repository(roomDao)
        val viewModelFactory = FeedsVmFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            FeedsFragmentViewModel::class.java
        )

        //Get all Posts from Both Api and DataBase
        viewModel.getAllPosts()

        //initialize the PostObj recycler view adapter
        post_recycler_view.adapter = adapter

        //Observer PostObj Live Data Source
        viewModel.combinedPostsData()?.observe(requireActivity(), Observer { response ->
            response.let {
                val combinedPosts: List<Post> = it.second + it.first
                adapter.setPosts(combinedPosts)
                //Define Layout manager
                post_recycler_view.layoutManager = LinearLayoutManager(requireContext())
            }
        })


        //Get reference to add post FAB and set an onClickListener to open the AddComment Dialog
        addPostFab = add_new_post_fab
        addPostFab.setOnClickListener {
            newPostDialog = AddNewPostDialog()
            newPostDialog.show(activity?.supportFragmentManager!!, "Add Dialog")
        }

        post_filter_button.setOnClickListener {
            viewModel.deleteAllPosts()
        }
    }

    //OnClick of a post, open the comment fragment and pass the PostObj item as a bundle
    override fun onItemClick(view: View, item: PostObj) {
        val action = FeedsFragmentDirections
            .actionFeedsFragmentToPostDetailFragment()
            .setPost(item)
        view.findNavController().navigate(action)
    }


    //override OnStop LifeCycle,  the fragment is not in view. Set fragment visibility to false
    //If, set fragment visibility to false
    override fun onStop() {
        super.onStop()
        isFragmentVisible = false
    }

}


