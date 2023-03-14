package com.walcker.core.model

import com.google.protobuf.Timestamp


data class BookUI(
    var id: String? = null,
    var title: String? = null,
    var authors: String? ="",
    var description: String? = "",
    var date: String? = null,
    var category: String? = "",
    var photoUrl: String? = null,
    var rating: Int? = 0,
    var notes: String? = "",
    var pageCount: Int? = null,
    var startedReading: Timestamp? = null,
    var finishedReading: Timestamp? = null,
    var userId: String? = null,
    var googleBookId: String? = null
)