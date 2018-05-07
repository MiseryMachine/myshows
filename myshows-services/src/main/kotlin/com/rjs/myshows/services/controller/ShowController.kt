package com.rjs.myshows.services.controller

import com.rjs.myshows.common.domain.ShowDto
import com.rjs.myshows.common.domain.UserShowFilterDto
import com.rjs.myshows.common.domain.mediaFormats
import com.rjs.myshows.common.domain.starRatings
import com.rjs.myshows.services.config.AppConfig
import com.rjs.myshows.services.domain.ShowEntity
import com.rjs.myshows.services.domain.dto.ShowFilterDto
import com.rjs.myshows.services.service.ShowService
import com.rjs.myshows.services.service.ShowTypeService
import org.modelmapper.ModelMapper

abstract class ShowController(
    protected val showService: ShowService,
    protected val showTypeService: ShowTypeService,
    protected val modelMapper: ModelMapper,
    protected val appConfig: AppConfig
) {

    protected fun initializeShowFilter() = ShowFilterDto(
        "Movie",
        mediaFormats[0],
        starRatings[starRatings.lastIndex]
    )

    protected fun buildSearchModel(
        showFilterDto: ShowFilterDto,
        userShowFilters: MutableList<UserShowFilterDto>,
        selUserFilter: String? = null,
        searchResults: MutableList<ShowEntity> = mutableListOf())
        : Map<String, Any?> {
        val model: MutableMap<String, Any?> = hashMapOf()
        val showDtos: List<ShowDto> =
            if (searchResults.isNotEmpty()) {
                searchResults.map{show -> convertToShowDto(show)}
            }
            else {
                listOf()
            }

        model["showTypes"] = showTypeService.getAll()
        model["starRatings"] = starRatings
        model["mediaFormats"] = mediaFormats
        model["userFilters"] = userShowFilters
        model["selUserFilter"] = selUserFilter
        model["showSearchFilter"] = showFilterDto
        model["numResults"] = showDtos.size
        model["shows"] = showDtos

        return model
    }

    protected fun convertToShowDto(show: ShowEntity) = modelMapper.map(show, ShowDto::class.java)!!
}