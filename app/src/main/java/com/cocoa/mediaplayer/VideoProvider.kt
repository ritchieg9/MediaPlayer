package com.cocoa.mediaplayer


import android.app.Activity
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.cocoa.mediaplayer.api.MovieDetails
import com.cocoa.mediaplayer.api.TheMovieDBClient
import com.cocoa.mediaplayer.api.TheMovieDBInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object VideoProvider : Activity() {

    const val API_KEY = "a6ea5bd224b1d6ca252e6209cca8b9d7"
    var list: List<MovieDetails> ?=null
    private var count: Long = 0

    @RequiresApi(Build.VERSION_CODES.Q)
    fun setupMovies(activity: Activity): List<MovieDetails>? {

        val proj = arrayOf(
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.ARTIST
        )

        val orderBy = MediaStore.Video.Media.TITLE
        val rs: Cursor? = activity.applicationContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, orderBy)

        val request = TheMovieDBClient.ServiceBuilder.buildService(TheMovieDBInterface::class.java)
        val call = request.getMovieDetails(650, API_KEY)

        Log.d("VIDEO","MAAAAAAAAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU")

        call.enqueue(object : Callback<MovieDetails>{
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful){
                    Log.d("VIDEO","ALGO MUY BUENO PASO")
                    Log.d("VIDEO",response.body().toString())
                }
            }
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.d("VIDEO","ALGO MUY MALO PASO")
            }
        })


        if (rs != null) {

            val title: MutableList<String> = mutableListOf()
            var videoUrl: MutableList<String> = mutableListOf()
            var movieDirector: MutableList<String> = mutableListOf()
            var bgImageUrl: MutableList<String> = mutableListOf()
            var cardImageUrl: MutableList<String> = mutableListOf()
            var description: MutableList<String> = mutableListOf()

            var count = 0
            while (rs.moveToNext()) {
                if(rs.getString(4) == "video/mp4"){
//                    Log.d("VIDEO", rs.getString(2))
                    title.add(rs.getString(2))
                    videoUrl.add(rs.getString(0))
                    movieDirector.add(rs.getString(7))
                    bgImageUrl.add(rs.getString(0))
                    cardImageUrl.add((rs.getString(0)))
                    description.add("Some Random Shit around")
                }
            }
            rs.close()


            list = title.indices.map {
                buildMovieInfo(
                    title[it],
                    description[it],
                    movieDirector[it],
                    videoUrl[it],
                    cardImageUrl[it],
                    bgImageUrl[it]
                )
            }
        }
        return list
    }

    fun buildMovieInfo(
        title: String,
        description: String,
        studio: String,
        videoUrl: String,
        cardImageUrl: String,
        backgroundImageUrl: String): MovieDetails {

        var movie = MovieDetails()
        movie.id = count++
        movie.backdrop_path = backgroundImageUrl
        movie.overview = description
        movie.poster_path = cardImageUrl
        movie.title = title
        movie.videoUrl = videoUrl
        movie.studio = studio


        return movie
    }
}