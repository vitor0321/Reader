package com.walcker.reader.framework.network.response

data class VolumeInfo(
    val authors: List<String>,
    val categories: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
    val publishedDate: String,
    val title: String
)