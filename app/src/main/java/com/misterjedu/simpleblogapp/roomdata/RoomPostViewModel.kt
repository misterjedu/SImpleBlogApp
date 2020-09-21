package com.misterjedu.simpleblogapp.roomdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class RoomPostViewModel(application: Application) : AndroidViewModel(application) {

    private val readPostData: LiveData<List<RoomPost>>
    private val repository: PostRepository

    init {
        val postDao = PostDataBase.getDatabase(application).postDao()
        repository = PostRepository(postDao)
        readPostData = repository.readAllPosts
    }

    fun addPost(post: RoomPost) {
        viewModelScope.launch(IO) {
            repository.addPost(post)
        }
    }
}