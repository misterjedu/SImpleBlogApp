package com.misterjedu.simpleblogapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterjedu.simpleblogapp.roomModel.Comment
import com.misterjedu.simpleblogapp.repository.IRepository
import com.misterjedu.simpleblogapp.roomModel.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class CommentsFragmentVieModel(private val repository: IRepository) : ViewModel() {

    //Live Data of Comments
    private val _allComments: MutableLiveData<List<Comment>> = MutableLiveData()
    private val allComments: LiveData<List<Comment>>
        get() = _allComments

    //Room Comment for One Post
    private lateinit var roomComments: LiveData<List<Comment>>


    //Get Comments for a Post from Room
    fun getRoomComments(postId: Int) {
        roomComments = repository.readComments(postId)
    }


    //Ad Comment to Room
    fun addComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addComment(comment)
        }
    }

    //Delete Posts from Room
    fun deleteAllComments() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllComment()
        }
    }


    //Make Retrofit call to get posts
    fun getComments(id: String) {
        viewModelScope.launch {
            try {
                if (repository.getComments(id).isSuccessful) {
                    val response: List<Comment>? = repository.getComments(id).body()
                    _allComments.value = response
                }

            } catch (e: IOException) {
                Log.i("Response", "No internet Connection ")
            }
        }
    }

    //Combined Live Data Source
    fun combinedCommentsData(): CombinedComments? {
        return CombinedComments(allComments, roomComments)
    }

}