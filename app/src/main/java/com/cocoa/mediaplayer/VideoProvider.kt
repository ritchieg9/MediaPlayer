package com.cocoa.mediaplayer


import android.app.Activity
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.leanback.widget.ArrayObjectAdapter
import com.cocoa.mediaplayer.api.MovieDetails
import com.cocoa.mediaplayer.api.MovieResults
import com.cocoa.mediaplayer.api.TheMovieDBClient
import com.cocoa.mediaplayer.api.TheMovieDBInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



object VideoProvider : Activity() {

    const val API_KEY = "a6ea5bd224b1d6ca252e6209cca8b9d7"
    const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500/"
    var list: List<MovieDetails> ?=null
    private var count: Long = 0

    private var mAdapter: ArrayObjectAdapter? = null


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

        // MAKE CALL WITH ID
//        val request = TheMovieDBClient.ServiceBuilder.buildService(TheMovieDBInterface::class.java)
//        val call = request.getMovieDetails(800, API_KEY)
//
//        Log.d("VIDEO","MAAAAAAAAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU")
//
//        call.enqueue(object : Callback<MovieDetails>{
//            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
//                if (response.isSuccessful){
//                    Log.d("VIDEO","ALGO MUY BUENO PASO")
//                    Log.d("VIDEO",response.body().toString())
//                }
//            }
//            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
//                Log.d("VIDEO","ALGO MUY MALO PASO")
//            }
//        })
        // MEND OF CALL

        // MAKE CALL Search
//        val request = TheMovieDBClient.ServiceBuilder.buildService(TheMovieDBInterface::class.java)
//        val call = request.searchMovie(API_KEY,"en-US", "placebo", "2015" )
//
//        Log.d("VIDEO","MAAAAAAAAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU")
//
//        call.enqueue(object : Callback<MovieResults>{
//            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
//                if (response.isSuccessful){
//                    Log.d("VIDEO","ALGO MUY BUENO PASO")
//                    Log.d("VIDEO",response.body().toString())
//                }
//            }
//            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
//                Log.d("VIDEO","ALGO MUY MALO PASO")
//            }
//        })
        // MEND OF CALL


        if (rs != null) {

            var title: MutableList<String> = mutableListOf()
            var videoUrl: MutableList<String> = mutableListOf()
            var movieDirector: MutableList<String> = mutableListOf()
            var bgImageUrl: MutableList<String> = mutableListOf()
            var cardImageUrl: MutableList<String> = mutableListOf()
            var description: MutableList<String> = mutableListOf()
            var done = false

            var count = 0
            while (rs.moveToNext()) {

                count++
                if(rs.getString(4) == "video/mp4"){
                    Log.d("VIDEO", rs.getString(2))

                    val request = TheMovieDBClient.ServiceBuilder.buildService(TheMovieDBInterface::class.java)
                    val call: Call<MovieResults> = request.searchMovie(API_KEY,"en-US", rs.getString(2))
                    var url = rs.getString(0)
                    val director= rs.getString(7)

//                    val callSync: Call<MovieResults> = request.searchMovie(API_KEY,"en-US", "placebo")
//
//                    try {
//                        val response: Response<MovieResults> = callSync.execute()
//                        val apiResponse: MovieResults? = response.body()
//
//                        //API response
//                        Log.d("VIDEO","MAAAAAAAAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU "+count)
//                    } catch (ex: Exception) {
//                        ex.printStackTrace()
//                    }


                    call.enqueue(object : Callback<MovieResults> {
                        override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                            if (response.isSuccessful){

                                Log.d("VIDEO","MAAAAAAAAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU "+count)
                                Log.d("VIDEO",response.body().toString())
                                title.add(response.body()!!.results[0].title.toString())
                                videoUrl.add(url)
                                movieDirector.add(director)
                                bgImageUrl.add(POSTER_BASE_URL+response.body()!!.results[0].backdrop_path.toString())
                                cardImageUrl.add(POSTER_BASE_URL+response.body()!!.results[0].poster_path.toString())
                                description.add(response.body()!!.results[0].overview.toString())

                                mAdapter = ArrayObjectAdapter(CardPresenter())

                                var movie = MovieDetails()
                                movie.id = VideoProvider.count++
                                movie.backdrop_path = POSTER_BASE_URL+response.body()!!.results[0].backdrop_path.toString()
                                movie.overview = response.body()!!.results[0].overview.toString()
                                movie.poster_path = POSTER_BASE_URL+response.body()!!.results[0].poster_path.toString()
                                movie.title = response.body()!!.results[0].title.toString()
                                movie.videoUrl = url
                                movie.studio = director

                                mAdapter!!.add(movie)
                            }
                        }
                        override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                            Log.d("VIDEO","ALGO MUY MALO PASO")
                        }
                    })

                    if (count == 5){
                        break
                    }
                }
            }

            Log.d("VIDEO","COCOOOOOOOOOOOOOOOOOOOOOAAAAAAAAAAAAAa ")
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