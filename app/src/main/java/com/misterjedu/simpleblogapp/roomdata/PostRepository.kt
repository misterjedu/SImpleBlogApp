package com.misterjedu.simpleblogapp.roomdata

import androidx.lifecycle.LiveData

class PostRepository(private val postDao: PostDao) {

    val readAllPosts: LiveData<List<RoomPost>> = postDao.readAllPost()

    suspend fun addPost(post: RoomPost) {
        postDao.addPost(post)
    }


}