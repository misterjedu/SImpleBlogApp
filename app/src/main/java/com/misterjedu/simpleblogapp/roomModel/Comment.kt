package com.misterjedu.simpleblogapp.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
class Comment(
    val postId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)