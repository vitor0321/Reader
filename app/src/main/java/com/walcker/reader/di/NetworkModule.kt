package com.walcker.reader.di

import com.walcker.reader.BuildConfig
import com.walcker.reader.data.network.BooksApi
import com.walcker.reader.di.Constants.TIMEOUT_SECONDS
import com.walcker.reader.domain.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = DI.Module("networkModule") {

    bind<HttpLoggingInterceptor>() with singleton {
        HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else HttpLoggingInterceptor.Level.NONE
            )
        }
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
            .addInterceptor(instance<HttpLoggingInterceptor>())
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    bind<GsonConverterFactory>() with singleton {
        GsonConverterFactory.create()
    }

    bind<BooksApi>() with singleton {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(instance())
            .addConverterFactory(instance())
            .build()
            .create(BooksApi::class.java)
    }
}

internal object Constants {
    const val TIMEOUT_SECONDS = 15L
}
