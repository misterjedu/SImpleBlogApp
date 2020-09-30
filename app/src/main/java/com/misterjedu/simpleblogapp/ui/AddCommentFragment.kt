package com.misterjedu.simpleblogapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.modelFactory.FeedsVmFactory
import com.misterjedu.simpleblogapp.repository.Repository
import com.misterjedu.simpleblogapp.roomModel.Comment
import com.misterjedu.simpleblogapp.roomModel.DataBase
import com.misterjedu.simpleblogapp.roomModel.RoomDao
import com.misterjedu.simpleblogapp.ui.dataclasses.PostObj
import com.misterjedu.simpleblogapp.viewmodel.CommentsFragmentVieModel
import kotlinx.android.synthetic.main.fragment_add_comment.*

class AddCommentFragment : Fragment() {
    private lateinit var viewModel: CommentsFragmentVieModel
    private lateinit var roomDao: RoomDao
    private lateinit var addCommentButton: Button
    private lateinit var addCommentView: EditText
    private var postId = 0
    private val args: AddCommentFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        postId = args.postId
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_comment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Get Comment edit text and button
        addCommentButton = add_comment_button
        addCommentView = add_comment_et


        //Instantiate Repository
        roomDao = DataBase.getPostDatabase(requireContext()).postDao()
        val repository = Repository(roomDao)
        val viewModelFactory = FeedsVmFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            CommentsFragmentVieModel::class.java
        )

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
    }
}