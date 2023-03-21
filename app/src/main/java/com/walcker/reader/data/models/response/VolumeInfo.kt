package com.walcker.reader.data.models.response

import com.google.gson.annotations.SerializedName

data class VolumeInfo(
    @SerializedName("authors")
    val authors: List<String>,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("pageCount")
    val pageCount: Int?,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks,
    @SerializedName("publishedDate")
    val publishedDate: String,
    @SerializedName("title")
    val title: String,
)