package com.misterjedu.simpleblogapp.repository

import com.misterjedu.simpleblogapp.api.RetrofitInstance
import com.misterjedu.simpleblogapp.model.RetroComment
import com.misterjedu.simpleblogapp.model.RetroPost
import retrofit2.Response

class Repository {

    //Get Comments
    suspend fun getComments(id : String): Response<List<RetroComment>> {
        return RetrofitInstance.api.getComments(id)
    }

    //Get all Posts
    suspend fun getAllPosts(): Response<List<RetroPost>> {
        return RetrofitInstance.api.getAllPosts()
    }

}