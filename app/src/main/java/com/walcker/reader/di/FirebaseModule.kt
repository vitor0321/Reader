package com.walcker.reader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val firebaseModule = DI.Module("firebaseModule") {

    bind<Query>() with singleton {
        FirebaseFirestore.getInstance()
            .collection("books")
    }
}