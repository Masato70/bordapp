package com.example.a.Post

import java.sql.Date

data class Post(
    val icon: Int = 0,
    val title: String,
    val detail: Long,
    var createAt: Date = Date(System.currentTimeMillis()),
)
