package com.misterjedu.simpleblogapp.roomModel

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomDao {
    //Add Post to Database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(post: Post)

    //Select all post from Room
    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun readAllPost(): LiveData<List<Post>>

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun _readAllPost(): List<Post>

    //Delete All Posts from Room
    @Query("DELETE FROM post_table")
    suspend fun deleteAllPosts()

    //Add Post to Database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment: Comment)

    //Select all comment from Room from one post
    @Query("SELECT * FROM comment_table WHERE postId = :id ORDER BY postId DESC")
    fun readComments(id: Int): LiveData<List<Comment>>

    //Select all post from Room
    @Query("SELECT * FROM comment_table ORDER BY postId ASC")
    suspend fun getAllRoomComment(): List<Comment>

    //Delete All Comments from Room
    @Query("DELETE FROM comment_table")
    suspend fun deleteAllComments()

}