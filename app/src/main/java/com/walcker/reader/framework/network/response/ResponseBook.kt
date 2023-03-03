package com.walcker.reader.framework.network.response

import com.google.gson.annotations.SerializedName

data class ResponseBook(
    @SerializedName("items")
    val items: List<Item>
)