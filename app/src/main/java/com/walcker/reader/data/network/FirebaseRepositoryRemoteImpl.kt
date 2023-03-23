package com.walcker.reader.data.network

import com.google.firebase.firestore.Query
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.data.mapper.ResponseItemMapper
import com.walcker.reader.data.models.BookUIFirebase
import com.walcker.reader.domain.FirebaseRepositoryRemote
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryRemoteImpl(
    private val queryBook: Query,
) : FirebaseRepositoryRemote {

    override suspend fun getAllBooksFromDatabase(): List<BookUI?> {
        val resultFirebase = queryBook.get().await().documents.map { documentSnapshot ->
            documentSnapshot.toObject(BookUIFirebase::class.java)
        }
        return resultFirebase.map {
            it?.let { ResponseItemMapper.mapToData(it) }
        }
    }
}