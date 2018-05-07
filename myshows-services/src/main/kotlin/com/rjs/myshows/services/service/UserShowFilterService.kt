package com.rjs.myshows.services.service

import com.rjs.myshows.services.domain.UserShowFilterEntity
import com.rjs.myshows.services.repository.UserShowFilterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userShowFilterService")
@Transactional
class UserShowFilterService(val userShowFilterRepository: UserShowFilterRepository
): BaseService<UserShowFilterEntity, UserShowFilterRepository>(userShowFilterRepository) {
    fun findByUserId(userId: Long) = userShowFilterRepository.findByUserId(userId)
    fun findByUsername(username: String) = userShowFilterRepository.findByUserUsername(username)

    fun deleteByFilterName(username: String, filterName: String) = userShowFilterRepository.deleteByUserUsernameAndName(username, filterName)
}