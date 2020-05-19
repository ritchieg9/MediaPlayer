package com.corochann.helloandroidtvfromscrach

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.leanback.app.VerticalGridFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.VerticalGridPresenter
import com.cocoa.mediaplayer.CardPresenter
import com.cocoa.mediaplayer.VideoProvider
import com.cocoa.mediaplayer.api.MovieDetails


class MainFragment : VerticalGridFragment() {

    private val TAG = VerticalGridFragment::class.java.simpleName
    private val NUM_COLUMNS = 5
    private var mAdapter: ArrayObjectAdapter? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("VIDEO", "onCreate VerticalGridFragment")
        setTitle("Movies");
        super.onCreate(savedInstanceState)

        // This is to request Read External storage permissions
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 505)
        }
        else{
            setupFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode === 505 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            setupFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public fun setupFragment() {

//        TODO Replace list with movie list
//        VideoProvider.setupMovies(activity)

        mAdapter = ArrayObjectAdapter(CardPresenter())
        val list: List<MovieDetails>? = VideoProvider.setupMovies(activity)
//        val list: List<String> ?= null
        val gridPresenter = VerticalGridPresenter()
        gridPresenter.setNumberOfColumns(NUM_COLUMNS)
        setGridPresenter(gridPresenter)

        if (list != null) {
            for (movie in list) {
                mAdapter!!.add(movie)
            }
        }

        adapter = mAdapter
    }

}