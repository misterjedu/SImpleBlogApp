package com.misterjedu.simpleblogapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.ui.dialogs.AddNewPostDialog
import kotlinx.android.synthetic.main.fragment_feeds.*

class FeedsFragment : Fragment() {

    private lateinit var addPostFab: FloatingActionButton
    private lateinit var newPostDialog: AddNewPostDialog

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

        //Get reference to add post FAB and set an onClickListener to open the AddComment Dialog
        addPostFab = add_new_post_fab
        addPostFab.setOnClickListener {
            newPostDialog = AddNewPostDialog()
            newPostDialog.show(activity?.supportFragmentManager!!,"Add Dialog")
        }
    }



}