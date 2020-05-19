package com.cocoa.mediaplayer;

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.leanback.app.VerticalGridFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.VerticalGridPresenter
import com.cocoa.mediaplayer.api.MovieDetails
import com.example.videostudio.R


class MainFragment : VerticalGridFragment() {

    private val TAG = VerticalGridFragment::class.java.simpleName
    private val NUM_COLUMNS = 5
    private var mAdapter: ArrayObjectAdapter? = null
    private val mSinnerFragment = SpinnerFragment()


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
            ShowSpinnerTask().execute()
        }
    }


    private inner class ShowSpinnerTask :
        AsyncTask<Void?, Void?, Void?>() {


        @RequiresApi(Build.VERSION_CODES.Q)
        @SuppressLint("ResourceType")
        override fun onPreExecute() {
            VideoProvider.setupMovies(activity)
//            activity.fragmentManager.beginTransaction().add(R.id.main_fragment, mSinnerFragment).commit()
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onPostExecute(aVoid: Void?) {

            Log.d("VIDEO", "NIGGAR D")
            setUpDone()
//            fragmentManager.beginTransaction().remove(mSinnerFragment).commit()
//            setAdapter(mAdapter);
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun doInBackground(vararg params: Void?): Void? {
            Log.d("VIDEO", "NIGGAR1")
            SystemClock.sleep(5000);
            Log.d("VIDEO", "NIGGAR2")
            return null
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

        Log.d("VIDEO","FUCKER")

//        TODO Replace list with movie list
//        VideoProvider.setupMovies(activity)

        mAdapter = ArrayObjectAdapter(CardPresenter())
//        val list: List<MovieDetails>? = VideoProvider.setupMovies(activity)
//        val list: List<String> ?= null
        val gridPresenter = VerticalGridPresenter()
        gridPresenter.setNumberOfColumns(NUM_COLUMNS)
        setGridPresenter(gridPresenter)

//        if (list != null) {
//            for (movie in list) {
//                mAdapter!!.add(movie)
//            }
//        }

        adapter = mAdapter

//        ShowSpinnerTask().execute()

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public fun setUpDone(){
        Log.d("VIDEO", "DRONE")
        val list: List<MovieDetails>? = VideoProvider.list
        if (list != null) {
            for (movie in list) {
                Log.d("VIDEO", "PELi")
                mAdapter!!.add(movie)
            }
        }

    }

}