package com.walcker.reader.framework.network.response

import com.google.gson.annotations.SerializedName
import com.walcker.core.model.BookUI

data class Item(
    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)

fun Item.toBookUI(): BookUI {
    return BookUI(
        id = this.id,
        title = this.volumeInfo.title,
        authors = if (this.volumeInfo.authors != null) this.volumeInfo.authors[0] else "",
        description = this.volumeInfo.description,
        date = this.volumeInfo.publishedDate,
        category = if (this.volumeInfo.categories != null) this.volumeInfo.categories[0] else "",
        photoUrl = this.volumeInfo.imageLinks.thumbnail.replace("http", "https"),
        pageCount = this.volumeInfo.pageCount
    )
}