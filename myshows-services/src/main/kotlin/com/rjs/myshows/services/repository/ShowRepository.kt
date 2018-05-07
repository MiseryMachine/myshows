package com.rjs.myshows.services.repository

import com.rjs.myshows.services.domain.ShowEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ShowRepository: BaseRepository<ShowEntity>, JpaSpecificationExecutor<ShowEntity> {
    fun findByMdbId(mdbId: String): ShowEntity?
    fun findByTitle(title: String): MutableList<ShowEntity>
}