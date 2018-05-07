package com.rjs.myshows.services.domain

import com.rjs.myshows.common.domain.Show
import java.time.LocalDate
import javax.persistence.*

@Entity
class ShowEntity: BaseEntity(), Show {
    @Column(name = "mdb_id")
    override var mdbId: String? = null

    @Column(name = "imdb_id")
    override var imdbId: String? = null

    override var title: String = ""

    @Column(name = "show_rating", length = 10, nullable = false)
    override var showRating: String = ""

    @Transient
    override var contentsArray: Array<String> = emptyArray()

    @Column(name = "tag_line", length = 511)
    override var tagline: String = ""

    @Column(length = 2000)
    override var description: String = ""

    @Column(name = "release_date")
    override var releaseDate: LocalDate? = null

    @Transient
    override var releaseDateText: String = ""

    override var runtime: Int = 0

    @Column(name = "show_type", length = 40, nullable = false)
    override var showType: String = ""

    @ElementCollection
    @CollectionTable(name = "show_genre", joinColumns = [(JoinColumn(name = "show_id"))])
    @Column(name = "genre", length = 40, nullable = false)
    @OrderBy(value = "genre")
    override var genres: MutableSet<String> = mutableSetOf()

    @Column(name = "media_format")
    override var mediaFormat: String = ""

    @Column(name = "my_notes", length = 2000)
    override var myNotes: String = ""

    @Column(name = "star_rating")
    override var starRating: Int = 0
}