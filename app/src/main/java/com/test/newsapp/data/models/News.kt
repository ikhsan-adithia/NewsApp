package com.test.newsapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val author: String,
    val source: String,
    val title: String,
    val url: String,
    val content: String,
    val banner: String,
    val publishDate: String
): Parcelable
