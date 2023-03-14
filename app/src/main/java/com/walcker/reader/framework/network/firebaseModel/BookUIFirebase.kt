package com.walcker.reader.framework.network.firebaseModel

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import com.google.protobuf.Timestamp
import com.walcker.core.model.BookUI

data class BookUIFirebase(
    @Exclude
    var id: String? = null,
    var title: String? = null,
    var authors: String? = null,
    var notes: String? = null,
    var date: String? = null,
    var category: String? = null,
    @get:PropertyName("photo_url")
    @set:PropertyName("photo_url")
    var photoUrl: String? = null,
    var rating: Int? = null,
    var description: String? = null,
    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount: Int? = null,
    @get:PropertyName("started_reading")
    @set:PropertyName("started_reading")
    var startedReading: Boolean? = null,
    @get:PropertyName("finished_reading")
    @set:PropertyName("finished_reading")
    var finishedReading: Boolean? = null,
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String? = null,
    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId: String? = null
)

fun BookUIFirebase.toBookUI(): BookUI {
    return BookUI(
        id = this.id,
        title = this.title,
        authors = this.authors ?: "",
        notes = this.notes,
        date = this.date,
        category = this.category ?: "",
        photoUrl = this.photoUrl,
        rating = this.rating,
        description = description ?: "",
        pageCount = this.pageCount,
        startedReading = startedReading,
        finishedReading = this.finishedReading,
        userId = this.userId,
        googleBookId = this.googleBookId
    )
}

fun BookUI.toBookUIFirebase(): BookUIFirebase {
    return BookUIFirebase(
        id = this.id,
        title = this.title,
        authors = this.authors,
        notes = this.notes,
        date = this.date,
        category = this.category,
        photoUrl = this.photoUrl,
        rating = this.rating,
        description = description,
        pageCount = this.pageCount,
        startedReading = startedReading,
        finishedReading = this.finishedReading,
        userId = this.userId,
        googleBookId = this.googleBookId
    )
}
