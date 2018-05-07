package com.rjs.myshows.services.repository

import com.rjs.myshows.services.domain.UserShowFilterEntity


interface UserShowFilterRepository: BaseRepository<UserShowFilterEntity> {
    fun findByUserId(id: Long): List<UserShowFilterEntity>
    fun findByUserUsername(username: String): List<UserShowFilterEntity>

    fun deleteByUserUsernameAndName(username: String, filterName: String)
}