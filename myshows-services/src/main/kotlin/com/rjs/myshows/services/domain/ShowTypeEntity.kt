package com.rjs.myshows.services.domain

import com.rjs.myshows.common.domain.ShowType
import javax.persistence.*

@Entity
class ShowTypeEntity: BaseEntity(), ShowType {
    @Column(unique = true, nullable = false, length = 20)
    override var name: String = ""

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "show_type_genre", joinColumns = [(JoinColumn(name = "show_type_id"))])
    @Column(name = "genre", length = 40, nullable = false)
    @OrderBy(value = "genre")
    override var genres: MutableSet<String> = mutableSetOf()

    @Transient
    override var ratings: MutableSet<String> = mutableSetOf()
}