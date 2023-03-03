package com.walcker.reader.framework.network

import com.walcker.reader.framework.network.response.Item
import com.walcker.reader.framework.network.response.ResponseBook
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {

    //https://www.googleapis.com/books/v1/volumes?q=android
    @GET("volumes")
    suspend fun getAllBooks(
        @Query("q") query: String
    ): ResponseBook

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(
        @Path("bookId") bookId: String
    ): Item
}