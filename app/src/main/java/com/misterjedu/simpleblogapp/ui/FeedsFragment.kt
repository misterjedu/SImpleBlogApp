package com.misterjedu.simpleblogapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.modelFactory.FeedsVmFactory
import com.misterjedu.simpleblogapp.repository.IRepository
import com.misterjedu.simpleblogapp.repository.Repository
import com.misterjedu.simpleblogapp.roomdata.Post
import com.misterjedu.simpleblogapp.roomdata.PostDao
import com.misterjedu.simpleblogapp.roomdata.PostDataBase
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
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("OnCreate", "ActivityCreated: Feeds ")

        //Instantiate Repository
        postDao = PostDataBase.getDatabase(requireContext()).postDao()
        repository = Repository(postDao)
        val viewModelFactory = FeedsVmFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            FeedsFragmentViewModel::class.java
        )

        //Get all Posts from Both Api and DataBase
        viewModel.getAllPosts()


        //If the fragment is in view, set fragment visibility to true
        isFragmentVisible = true
        //Get reference to add post FAB and set an onClickListener to open the AddComment Dialog
        addPostFab = add_new_post_fab
        addPostFab.setOnClickListener {
            newPostDialog = AddNewPostDialog()
            newPostDialog.show(activity?.supportFragmentManager!!, "Add Dialog")
        }

        //initialize the PostObj recycler view adapter
        post_recycler_view.adapter = adapter

        //Observer PostObj Live Data Source
        viewModel.allPosts.observe(requireActivity(), Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    adapter.setPosts(it)
                }

                //Define Layout manager
                post_recycler_view.layoutManager = LinearLayoutManager(requireContext())

            } else {
                Log.i("Response", response.errorBody().toString())
            }
        })
    }

    //OnClick of a post, open the comment fragment and pass the PostObj item as a bundle
    override fun onItemClick(item: PostObj) {
        val fragment = PostDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("posts", item)
        fragment.arguments = bundle
        loadCommentFragment(fragment)
    }

    //Launch the comment fragment
    private fun loadCommentFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity_frameLayout, fragment, "tag")
            .addToBackStack(null).commit()
    }

    //override OnStop LifeCycle,  the fragment is not in view. Set fragment visibility to false
    //If, set fragment visibility to false
    override fun onStop() {
        super.onStop()
        isFragmentVisible = false
    }

}


//Change Page based on internet connection
//        val networkConnection = NetWorkConnection(activity?.applicationContext!!)
//        networkConnection.observe(requireActivity(), {
//            if (isFragmentVisible) {
//                if(it){
//                   isConnection = true
//                    viewModel.getAllPosts()
//                    Toast.makeText(requireContext(), isConnection.toString(), Toast.LENGTH_SHORT).show()
//                }else{
//                   isConnection = false
//                    Toast.makeText(requireContext(), isConnection.toString(), Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
