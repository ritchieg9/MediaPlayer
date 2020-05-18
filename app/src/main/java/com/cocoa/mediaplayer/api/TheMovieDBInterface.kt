package com.cocoa.mediaplayer.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id" ) id: Int): Single<MovieDetails>

    @GET("/3/movie/500")
    fun getMovies(@Query("api_key") key: String): Call<MovieDetails>
}