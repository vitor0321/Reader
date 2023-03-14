package com.walcker.reader.framework.network.response

import com.google.gson.annotations.SerializedName

data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String
)