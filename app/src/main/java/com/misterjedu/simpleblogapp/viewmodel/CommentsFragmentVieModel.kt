package com.misterjedu.simpleblogapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterjedu.simpleblogapp.model.RetroComment
import com.misterjedu.simpleblogapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class CommentsFragmentVieModel (private val repository: Repository) : ViewModel(){

    //Live Data of posts
    val allComments : MutableLiveData<Response<List<RetroComment>>> = MutableLiveData()

    //Make Retrofit call to get posts
    fun getComments(id: String){
        viewModelScope.launch{
            val response: Response<List<RetroComment>> = repository.getComments(id)
           allComments.value = response
        }
    }

}