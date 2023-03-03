package com.walcker.reader.framework.network.response

import com.walcker.core.model.BookUI

data class Item(
    val id: String,
    val volumeInfo: VolumeInfo
)

fun Item.toBookUI():BookUI{
    return BookUI(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors,
        notes = this.volumeInfo.description,
        date = this.volumeInfo.publishedDate,
        category = this.volumeInfo.categories,
        images = this.volumeInfo.imageLinks.thumbnail
    )
}

fun List<Item>.toListBookUI():List<BookUI>{
    val listBookUI = mutableListOf<BookUI>()
    this.map {
       listBookUI.add(
           BookUI(
               id = it.id,
               title = it.volumeInfo.title,
               authors = it.volumeInfo.authors,
               notes = it.volumeInfo.description,
               date = it.volumeInfo.publishedDate,
               category = it.volumeInfo.categories,
               images = it.volumeInfo.imageLinks.thumbnail
           )
       )
    }
    return listBookUI
}