package com.misterjedu.simpleblogapp.repository

import androidx.lifecycle.LiveData
import com.misterjedu.simpleblogapp.api.RetrofitInstance
import com.misterjedu.simpleblogapp.model.RetroComment
import com.misterjedu.simpleblogapp.roomdata.PostDao
import com.misterjedu.simpleblogapp.roomdata.Post
import retrofit2.Response

interface IRepository {
    //Get Comments
    suspend fun getComments(id : String): Response<List<RetroComment>>

    //Get all Posts
    suspend fun getAllPosts(): Response<List<Post>>
    fun injectDao(postDao: PostDao){
        TODO()
    }

    //Read PostObj from Room
    fun readPost(): LiveData<List<Post>>

    //Add PostObj to Room
    suspend fun addPost(post: Post)
}

class Repository(private val postDao: PostDao) : IRepository {

    //Get Comments
    override suspend fun getComments(id : String): Response<List<RetroComment>> {
        return RetrofitInstance.api.getComments(id)
    }

    //Get all Posts
    override suspend fun getAllPosts(): Response<List<Post>> {
        return RetrofitInstance.api.getAllPosts()
    }

    //Read PostObj from Room
    override fun readPost(): LiveData<List<Post>> {
//        this.postDao = postDao
        return postDao.readAllPost()
    }

    //Add PostObj to Room
    override suspend fun addPost(post: Post) {
        postDao.addPost(post)
    }
}