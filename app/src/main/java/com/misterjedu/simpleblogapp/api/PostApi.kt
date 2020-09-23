package com.misterjedu.simpleblogapp.api

import com.misterjedu.simpleblogapp.roomModel.Comment
import com.misterjedu.simpleblogapp.roomModel.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    //Get Comment
    @GET("posts/{id}/comments")
    suspend fun getComments(
        @Path("id") id: String,
    ): Response<List<Comment>>


    //Get all Posts
    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>

}