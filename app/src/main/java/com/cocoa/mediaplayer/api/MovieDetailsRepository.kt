package com.cocoa.mediaplayer.api

import android.app.Service
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService: TheMovieDBInterface){
    lateinit var movieDetailsNetowrkSource : MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails>{

        movieDetailsNetowrkSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)

        movieDetailsNetowrkSource.fetchMovieDetails(movieId)

        return movieDetailsNetowrkSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetowrkSource.networkState
    }
}