package com.misterjedu.simpleblogapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterjedu.simpleblogapp.model.RetroPost
import com.misterjedu.simpleblogapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class FeedsFragmentViewModel(private val repository: Repository) : ViewModel() {

    //Live Data of posts
    val allPosts : MutableLiveData<Response<List<RetroPost>>> = MutableLiveData()

    //Make Retrofit call to get posts
    fun getAllPosts(){
        viewModelScope.launch{
            val response: Response<List<RetroPost>> = repository.getAllPosts()

            if(response.isSuccessful){
                allPosts.value = response
            }else{
                Log.d("Response", "No Internet Connection ")
            }



        }
    }
}