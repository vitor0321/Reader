package com.walcker.reader.data.network.repository

import com.google.firebase.firestore.Query
import com.walcker.domain.data.repository.FirebaseRepositoryRemote
import com.walcker.domain.model.BookUI
import com.walcker.reader.data.network.firebaseModel.BookUIFirebase
import com.walcker.reader.data.network.firebaseModel.toBookUI
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryRemoteImpl @Inject constructor(
    private val queryBook: Query
) : FirebaseRepositoryRemote {

    override suspend fun getAllBooksFromDatabase(): List<BookUI?> {
        val resultFirebase = queryBook.get().await().documents.map { documentSnapshot ->
            documentSnapshot.toObject(BookUIFirebase::class.java)
        }
        return resultFirebase.map {
            it?.toBookUI()
        }
    }
}