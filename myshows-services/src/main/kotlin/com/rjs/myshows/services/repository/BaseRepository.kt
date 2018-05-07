package com.rjs.myshows.services.repository

import com.rjs.myshows.services.domain.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BaseRepository<E: BaseEntity>: JpaRepository<E, Long>