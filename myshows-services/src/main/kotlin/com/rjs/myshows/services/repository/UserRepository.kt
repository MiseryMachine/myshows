package com.rjs.myshows.services.repository

import com.rjs.myshows.services.domain.UserEntity


interface UserRepository: BaseRepository<UserEntity> {
    fun findByUsername(username: String): UserEntity?

    fun findByEmail(email: String): UserEntity?

    fun findByUsernameAndPassword(username: String, password: String): UserEntity?
}