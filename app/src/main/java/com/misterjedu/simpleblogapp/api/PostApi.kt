package com.misterjedu.simpleblogapp.api

import com.misterjedu.simpleblogapp.model.RetroComment
import com.misterjedu.simpleblogapp.model.RetroPost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    //Get Comment
    @GET("posts/{id}/comments")
    suspend fun getComments(
        @Path("id") id: String,
    ): Response<List<RetroComment>>


    //Get all Posts
    @GET("posts")
    suspend fun getAllPosts(): Response<List<RetroPost>>

}