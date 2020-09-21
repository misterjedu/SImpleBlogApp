package com.misterjedu.simpleblogapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterjedu.simpleblogapp.api.RetrofitInstance.api
import com.misterjedu.simpleblogapp.model.RetroPost
import com.misterjedu.simpleblogapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


class FeedsFragmentViewModel(private val repository: Repository) : ViewModel() {

    //Live Data of posts
    val allPosts: MutableLiveData<Response<List<RetroPost>>> = MutableLiveData()

    //Make Retrofit call to get posts
    fun getAllPosts() {
        viewModelScope.launch {
            try {
                val response: Response<List<RetroPost>> = repository.getAllPosts()
                allPosts.value = response
            } catch (e: IOException) {
                Log.i("Response", "No internet Connection ")
            }
        }
    }
}