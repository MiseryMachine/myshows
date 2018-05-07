package com.rjs.myshows.services.domain

import com.rjs.myshows.common.domain.UserShowFilter
import javax.persistence.*

@Entity
@Table(name = "user_show_filter", uniqueConstraints = [(UniqueConstraint(columnNames = ["user_id", "name"]))])
class UserShowFilterEntity: BaseEntity(), UserShowFilter {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null

    @Column(length = 40, nullable = false)
    override var name: String = ""

    @Column(name = "show_type", length = 40, nullable = false)
    override var showTypeName: String = ""

    override var title: String = ""

    @Column(name = "star_rating")
    override var starRating: Int = 0

    @Column(name = "media_format")
    override var format: String = ""

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_show_filter_genre", joinColumns = [(JoinColumn(name = "user_show_filter_id"))])
    @Column(name = "genre", length = 40, nullable = false)
    @OrderBy("genre")
    override var genres: MutableSet<String> = linkedSetOf()
}