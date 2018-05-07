package com.rjs.myshows.services.service.mdb.tmdb

class TmdbUrl(var url: String = "") {
    fun addPath(path: String): TmdbUrl {
        url += path

        return this
    }
}