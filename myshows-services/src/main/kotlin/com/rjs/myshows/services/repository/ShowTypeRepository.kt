package com.rjs.myshows.services.repository

import com.rjs.myshows.services.domain.ShowTypeEntity


interface ShowTypeRepository: BaseRepository<ShowTypeEntity> {
    fun findByName(name: String): ShowTypeEntity?
}