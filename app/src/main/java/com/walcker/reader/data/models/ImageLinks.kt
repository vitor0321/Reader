package com.walcker.reader.data.models

import com.google.gson.annotations.SerializedName

data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String
)