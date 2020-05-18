package com.cocoa.mediaplayer.api

data class MovieResults(
    val results: List<MovieDetails>,
    val total_results: Int
)