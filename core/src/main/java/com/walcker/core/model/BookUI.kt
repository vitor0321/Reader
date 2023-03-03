package com.walcker.core.model

data class BookUI(
    var id: String? = null,
    var title: String? = null,
    var authors: List<String>? = null,
    var notes: String? = null,
    var date: String? = null,
    var category: List<String>? = null,
    var images: String? = null
)
