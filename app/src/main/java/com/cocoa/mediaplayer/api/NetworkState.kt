package com.cocoa.mediaplayer.api

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState (val status: Status, val msg: String){

    companion object {
        var LOADED: NetworkState
        var LOADING: NetworkState
        var ERROR: NetworkState


        init{
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            ERROR = NetworkState(Status.FAILED, "Running")
        }
    }

}