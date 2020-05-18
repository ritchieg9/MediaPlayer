package com.cocoa.mediaplayer.api

import java.io.Serializable

data class MovieDetails(
    var id: Long = 0,
    var backdrop_path: String? = null,
    var overview: String? = null,
    var poster_path: String? = null,
    var title: String? = null,
    var videoUrl: String? = null,
    var studio:String? = null
): Serializable {

    override fun toString(): String {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", backgroundImageUrl='" + backdrop_path + '\'' +
                ", cardImageUrl='" + poster_path + '\'' +
                '}'
    }

    companion object {
        internal const val serialVersionUID = 727566175075960653L
    }
}
