package com.misterjedu.simpleblogapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterjedu.simpleblogapp.repository.IRepository
import com.misterjedu.simpleblogapp.roomModel.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class FeedsFragmentViewModel(private val repository: IRepository) : ViewModel() {

    //All Data from the API
    private val _readApiPosts: MutableLiveData<List<Post>> = MutableLiveData()
    private val readApiPosts: LiveData<List<Post>>
        get() = _readApiPosts

    //Read Posts from Room
    private val _readRoomPosts: LiveData<List<Post>> = repository.readPost()

    //Make Retrofit call to get posts
    fun getAllPosts() {
        viewModelScope.launch {
            try {
                if (repository.getAllPosts().isSuccessful) {
                    val response: List<Post>? = repository.getAllPosts().body()
                    _readApiPosts.value = response
                }

            } catch (e: IOException) {
                Log.i("Response", "No internet Connection ")
            }
        }
    }

    //Get Size of all posts in Room
    fun getAllPostList(): Int {
        var allRoomPosts = 0
        viewModelScope.launch(Dispatchers.IO) {
            val posts = repository.getAllPostList().size
            withContext(Dispatchers.Main) {
                allRoomPosts = posts
            }
        }
        return allRoomPosts
    }


    //Combined Live Data Source
    fun combinedPostsData(): CombinedPosts? {
        if (readApiPosts != null && _readRoomPosts != null) {
            return CombinedPosts(readApiPosts, _readRoomPosts)
        }
        return null
    }

    //Ad Post to Room
    fun addPost(post: Post) {
        viewModelScope.launch(IO) {
            repository.addPost(post)
        }
    }

    //Delete Posts from Room
    fun deleteAllPosts() {
        viewModelScope.launch(IO) {
            repository.deleteAllPosts()
        }
    }
}