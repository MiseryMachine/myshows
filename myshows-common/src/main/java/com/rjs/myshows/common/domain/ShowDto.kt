package com.rjs.myshows.domain

import java.time.LocalDate

class ShowDto: BaseDto(), Show {
    override var mdbId: String? = null
    override var imdbId: String? = null
    override var showRating: String = ""
    override var contentsArray: Array<String> = emptyArray()
    override var tagline: String = ""
    override var description: String = ""
    override var releaseDate: LocalDate? = null
    override var releaseDateText: String = ""
    override var runtime: Int = 0
    override var showType: String = ""
    override var genres: MutableSet<String> = mutableSetOf()
    override var mediaFormat: String = ""
    override var myNotes: String = ""
    override var starRating: Int = 0
}