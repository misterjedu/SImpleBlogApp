package com.misterjedu.simpleblogapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.modelFactory.FeedsVmFactory
import com.misterjedu.simpleblogapp.repository.IRepository
import com.misterjedu.simpleblogapp.repository.Repository
import com.misterjedu.simpleblogapp.roomModel.Post
import com.misterjedu.simpleblogapp.roomModel.RoomDao
import com.misterjedu.simpleblogapp.roomModel.DataBase
import com.misterjedu.simpleblogapp.viewmodel.FeedsFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO


class AddNewPostDialog : AppCompatDialogFragment() {

    private lateinit var userImage: ImageView
    private lateinit var postTitle: TextView
    private lateinit var postBody: TextView
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button
    private lateinit var viewModel: FeedsFragmentViewModel
    private lateinit var repository: IRepository
    private lateinit var roomDao: RoomDao


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        //Create an Alert Dialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater

        //Inflate the view
        val view: View = inflater.inflate(R.layout.add_new_post, null)

        /**
         * Get reference to all Views in the add dialog
         */
        userImage = view.findViewById(R.id.add_author_image)
        postTitle = view.findViewById(R.id.add_post_title)
        postBody = view.findViewById(R.id.add_post_body)
        submitButton = view.findViewById(R.id.submit_post_button)
        cancelButton = view.findViewById(R.id.cancel_post_button)

        //Instantiate Repository
        roomDao = DataBase.getPostDatabase(requireContext()).postDao()
        repository = Repository(roomDao)
        val viewModelFactory = FeedsVmFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            FeedsFragmentViewModel::class.java
        )


        //Add Post to room on Click
        submitButton.setOnClickListener {
            //Trigger the database to get all posts from database
            viewModel.getAllPostList()

            //Observe the size of the database and add new post on incremented Id
            viewModel._allRoomPostSize.observe(requireActivity(), {
                val id = it + 101
                if (postBody.text.isEmpty() || postBody.text.isEmpty()) {
                    Toast.makeText(requireContext(), "Add a title and post", Toast.LENGTH_LONG)
                        .show()
                } else {

                    val post =
                        Post(11, id, postTitle.text.toString(), postBody.text.toString())

                    //Add new Post to room via the Repository
                    viewModel.addPost(post)

                    Toast.makeText(requireContext(), "Post Added to Room", Toast.LENGTH_SHORT)
                        .show()
                    dialog?.dismiss()
                }
            })
        }

        //Close the Dialog when cancel button clicked
        cancelButton.setOnClickListener {
            dialog?.dismiss();
        }

        builder.setView(view)
        return builder.create()
    }

}