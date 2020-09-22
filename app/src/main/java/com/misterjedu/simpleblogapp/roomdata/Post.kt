package com.misterjedu.simpleblogapp.roomdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class Post(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)