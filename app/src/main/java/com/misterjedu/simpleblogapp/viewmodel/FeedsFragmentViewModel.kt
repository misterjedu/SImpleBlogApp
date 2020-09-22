package com.misterjedu.simpleblogapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterjedu.simpleblogapp.repository.IRepository
import com.misterjedu.simpleblogapp.roomdata.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class FeedsFragmentViewModel(private val repository: IRepository) : ViewModel() {

    //Live Data of posts
    val allPosts: MutableLiveData<Response<List<Post>>> = MutableLiveData()

    //Make Retrofit call to get posts
    fun getAllPosts() {
        viewModelScope.launch {
            try {
                val response: Response<List<Post>> = repository.getAllPosts()
                allPosts.value = response
            } catch (e: IOException) {
                Log.i("Response", "No internet Connection ")
            }
        }
    }

    //Read Posts from Room
    private val readPostData: LiveData<List<Post>> = repository.readPost()

    //Ad PostObj to Room
    fun addPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(post)
        }
    }


}