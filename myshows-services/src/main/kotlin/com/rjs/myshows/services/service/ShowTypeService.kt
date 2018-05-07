package com.rjs.myshows.services.service

import com.rjs.myshows.services.domain.ShowTypeEntity
import com.rjs.myshows.services.repository.ShowTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("showTypeService")
@Transactional
class ShowTypeService(private val showTypeRepository: ShowTypeRepository): BaseService<ShowTypeEntity, ShowTypeRepository>(showTypeRepository) {
    fun findByName(name: String) = showTypeRepository.findByName(name)
}