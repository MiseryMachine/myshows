package com.rjs.myshows.domain

import java.time.LocalDate

interface Show: BaseElement {
    var mdbId: String?
    var imdbId: String?
    var showRating: String
    var contentsArray: Array<String>
    var tagline: String
    var description: String
    var releaseDate: LocalDate?
    var releaseDateText: String
    var runtime: Int
    var showType: String
    var genres: MutableSet<String>
    var mediaFormat: String
    var myNotes: String
    var starRating: Int

    val genreText: String
        get() = genres.joinToString()
}