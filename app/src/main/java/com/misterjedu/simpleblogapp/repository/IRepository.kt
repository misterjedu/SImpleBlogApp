package com.misterjedu.simpleblogapp.repository

import androidx.lifecycle.LiveData
import com.misterjedu.simpleblogapp.roomModel.Comment
import com.misterjedu.simpleblogapp.roomModel.Post
import retrofit2.Response

interface IRepository {
    //Get Comments from Api
    suspend fun getComments(id: String): Response<List<Comment>>

    //Get comments from RoomDatabase
    fun readComments(roomId: Int): LiveData<List<Comment>>

    //Add comment to RoomDatabase
    suspend fun addComment(comment: Comment)

    //Delete Post from Room
    suspend fun deleteAllComment()

    //Get all Posts from Api
    suspend fun getAllPosts(): Response<List<Post>>

    //Read PostObj from Room
    fun readPost(): LiveData<List<Post>>

    //Get all Posts List from room
    suspend fun getAllPostList(): List<Post>

    //Add PostObj to Room
    suspend fun addPost(post: Post)

    //Delete Post from Room
    suspend fun deleteAllPosts()

    //Get All Room Comments
    suspend fun getAllRoomComments(): List<Comment>

}