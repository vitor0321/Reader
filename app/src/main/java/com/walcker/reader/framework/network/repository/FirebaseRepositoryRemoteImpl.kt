package com.walcker.reader.framework.network.repository

import com.google.firebase.firestore.Query
import com.walcker.core.data.repository.FirebaseRepositoryRemote
import com.walcker.core.model.BookUI
import com.walcker.reader.framework.network.firebaseModel.BookUIFirebase
import com.walcker.reader.framework.network.firebaseModel.toBookUI
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