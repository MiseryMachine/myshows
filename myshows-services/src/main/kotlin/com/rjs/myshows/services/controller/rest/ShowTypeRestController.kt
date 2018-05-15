package com.rjs.myshows.services.controller.rest

import com.rjs.myshows.common.domain.ShowTypeDto
import com.rjs.myshows.services.service.ShowTypeService
import org.modelmapper.ModelMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/show-types")
class ShowTypeRestController(private val showTypeService: ShowTypeService, private val modelMapper: ModelMapper) {
    @GetMapping("/all")
    fun getAllShowTypes(): List<ShowTypeDto> {
        val showTypes = showTypeService.getAll()

        return showTypes.map { showTypeEntity -> modelMapper.map(showTypeEntity, ShowTypeDto::class.java) }
    }
}