package com.misterjedu.simpleblogapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.misterjedu.simpleblogapp.R
import java.lang.ClassCastException


class AddNewPostDialog : AppCompatDialogFragment() {

    private lateinit var userImage: ImageView
    private lateinit var postTitle: TextView
    private lateinit var postBody: TextView
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button

    private lateinit var addPostDialogListener: AddPostDialogListener

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

        /**
         * Set Onclick Listeners
         */
        submitButton.setOnClickListener {
            if (postBody.text.isEmpty() || postBody.text.isEmpty()) {
                Toast.makeText(requireContext(), "Add a title and post", Toast.LENGTH_LONG).show()
            } else {

                addPostDialogListener.dialogClick(
                    postTitle.text.toString(),
                    postBody.text.toString()
                )
                dialog?.dismiss()
            }
        }

        //Close the Dialog when cancel button clicked
        cancelButton.setOnClickListener {
            dialog?.dismiss();
        }

        builder.setView(view)
        return builder.create()
    }

    //Cast context as AddPostDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            addPostDialogListener = context as AddPostDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$context must Implement AddPostDialogListener"
            )
        }
    }

    //Interface to communicate with Activity
    interface AddPostDialogListener {
        fun dialogClick(title: String, body: String)
    }
}