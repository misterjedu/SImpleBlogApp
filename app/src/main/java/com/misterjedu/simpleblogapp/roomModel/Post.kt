package com.misterjedu.simpleblogapp.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class Post(
    val userId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val body: String
)