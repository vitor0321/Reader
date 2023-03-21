package com.walcker.reader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
   @Provides
   fun provideBookApi(): Query{
       return FirebaseFirestore.getInstance()
           .collection("books")
  }
}