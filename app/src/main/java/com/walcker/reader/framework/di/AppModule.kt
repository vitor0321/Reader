package com.walcker.reader.framework.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.walcker.core.data.utils.Constants.BASE_URL
import com.walcker.reader.framework.network.BooksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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