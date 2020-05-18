package com.cocoa.mediaplayer.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {
    //https://api.themoviedb.org/3/movie/500?api_key=a6ea5bd224b1d6ca252e6209cca8b9d7&language=en-US
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id" ) id: Int, @Query("api_key") key: String): Call<MovieDetails>

    //https://api.themoviedb.org/3/search/movie?api_key=a6ea5bd224b1d6ca252e6209cca8b9d7&language=en-US&query=placebo&year=2015
    @GET("search/movie")
    fun searchMovie(@Query("api_key") key: String, @Query("language") language: String, @Query("query") query: String): Call<MovieResults>
}