package com.walcker.reader.data.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import com.walcker.reader.domain.model.BookUI

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