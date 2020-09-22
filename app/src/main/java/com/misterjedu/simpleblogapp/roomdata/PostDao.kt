package com.misterjedu.simpleblogapp.roomdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(post: Post)


    @Query("SELECT * FROM post_table ORDER BY id ASC")
    fun readAllPost(): LiveData<List<Post>>


}