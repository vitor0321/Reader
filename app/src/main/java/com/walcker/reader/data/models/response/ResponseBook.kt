package com.walcker.reader.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseBook(
    @SerializedName("items")
    val items: List<Item>
)