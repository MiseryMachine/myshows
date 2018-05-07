package com.rjs.myshows.services.service.mdb

import com.rjs.myshows.services.domain.ShowEntity
import com.rjs.myshows.services.domain.mdb.MdbShow


interface MdbService {
    fun searchShows(showTypeName: String, title: String): MutableList<MdbShow>

    fun addShow(showTypeName: String, mdbId: String): ShowEntity?

    fun getGenres(showTypeName: String): MutableSet<String>
}