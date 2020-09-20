package com.misterjedu.simpleblogapp.ui.dataclasses

data class Post(
    var id: String,
    var date: String,
    var post: String,
    var username: String,
    var tag: String,
    var readTime: String
)