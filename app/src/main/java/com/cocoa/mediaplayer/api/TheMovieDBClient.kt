package com.cocoa.mediaplayer.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "a6ea5bd224b1d6ca252e6209cca8b9d7"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500/"

object TheMovieDBClient {

    object ServiceBuilder {
        private val client = OkHttpClient.Builder().build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun<T> buildService(service: Class<T>): T{
            return retrofit.create(service)
        }
    }

//    fun getClient(): TheMovieDBInterface{
//
//        var requestInterceptor = Interceptor { chain ->
//
//            val url = chain.request()
//                .url().newBuilder()
//                .addQueryParameter("api_key", API_KEY)
//                .build()
//
//            val request = chain.request()
//                .newBuilder()
//                .url(url)
//                .build()
//
//            return@Interceptor chain.proceed(request)
//
//        }
//
//        val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor).connectTimeout(60, TimeUnit.SECONDS).build()
//
//
//        return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL).addCallAdapterFactory(
//            RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(TheMovieDBInterface::class.java)
//    }
}