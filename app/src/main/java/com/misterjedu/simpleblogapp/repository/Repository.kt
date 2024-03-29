package com.misterjedu.simpleblogapp.repository

import androidx.lifecycle.LiveData
import com.misterjedu.simpleblogapp.api.RetrofitInstance
import com.misterjedu.simpleblogapp.roomModel.Comment
import com.misterjedu.simpleblogapp.roomModel.RoomDao
import com.misterjedu.simpleblogapp.roomModel.Post
import retrofit2.Response


class Repository(private val dao: RoomDao) : IRepository {

    //Get Comments from Api
    override suspend fun getComments(id: String): Response<List<Comment>> {
        return RetrofitInstance.api.getComments(id)
    }

    override fun readComments(roomId: Int): LiveData<List<Comment>> {
        return dao.readComments(roomId)
    }

    override suspend fun addComment(comment: Comment) {
        dao.addComment(comment)
    }

    override suspend fun getAllRoomComments(): List<Comment> {
        return dao.getAllRoomComment()
    }

    override suspend fun deleteAllComment() {
        dao.deleteAllComments()
    }

    //Get posts from Api
    override suspend fun getAllPosts(): Response<List<Post>> {
        return RetrofitInstance.api.getAllPosts()
    }

    //Read Posts from Room
    override fun readPost(): LiveData<List<Post>> {
        return dao.readAllPost()
    }

    override suspend fun getAllPostList(): List<Post> {
        return dao._readAllPost()
    }

    //Add PostObs to Room
    override suspend fun addPost(post: Post) {
        dao.addPost(post)
    }

    //Delete Posts from room
    override suspend fun deleteAllPosts() {
        dao.deleteAllPosts()
    }

}