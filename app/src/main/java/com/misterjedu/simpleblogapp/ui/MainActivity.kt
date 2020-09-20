package com.misterjedu.simpleblogapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.misterjedu.simpleblogapp.R
import com.misterjedu.simpleblogapp.ui.dialogs.AddNewPostDialog

class MainActivity : AppCompatActivity(), AddNewPostDialog.AddPostDialogListener {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        val fragment: Fragment = FeedsFragment()

        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_activity_frameLayout, fragment).addToBackStack(null)
            .commit()
    }

    //Implement the DialogClick Interface
    override fun dialogClick(title: String, body: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
    }
}