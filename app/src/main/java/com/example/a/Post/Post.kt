package com.example.a.Post

import java.sql.Date

data class Post(
    val icon: Int,
    val title: String,
    val detail: String,
    var createAt: Date = Date(System.currentTimeMillis()),
)
