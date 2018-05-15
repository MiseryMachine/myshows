package com.rjs.myshows.services.controller.rest

import com.rjs.myshows.common.domain.ShowDto
import com.rjs.myshows.common.domain.ShowFilterDto
import com.rjs.myshows.services.config.AppConfig
import com.rjs.myshows.services.controller.ShowController
import com.rjs.myshows.services.domain.ShowEntity
import com.rjs.myshows.services.service.ShowService
import com.rjs.myshows.services.service.ShowTypeService
import org.modelmapper.ModelMapper
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ws/shows")
class ShowRestController(showService: ShowService,
                         showTypeService: ShowTypeService,
                         modelMapper: ModelMapper,
                         appConfig: AppConfig
): ShowController(showService, showTypeService, modelMapper, appConfig) {
    @GetMapping("/all")
    fun getAllShows() = showService.getAll()

    @GetMapping("/{id}")
    fun getShow(@PathVariable("id") id: Long) = showService.findById(id)

    @GetMapping("/search-form")
    fun getSearchFormData() = buildSearchModel(initializeShowFilter(), mutableListOf())

    @PostMapping("/search")
    fun searchShows(@RequestBody showFilterDto: ShowFilterDto): List<ShowDto> =
        showService.searchShows(showFilterDto).map{show -> convertToShowDto(show)}

    @GetMapping("/details/{showId}")
    fun getShowDetails(@PathVariable("showId") showId: Long): ShowEntity? {
        val show = showService.findById(showId)

        if (show?.releaseDate != null) {
            show.releaseDateText = appConfig.dateFormat().format(show.releaseDate)
        }

        return show
    }

    @GetMapping(value = ["/poster/{showId}"], produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun getPosterImage(@PathVariable("showId") showId: Long) = showService.getShowPosterData(showId, false)

    @GetMapping(value = ["/poster-thumb/{showId}"], produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun getPosterThumb(@PathVariable("showId") showId: Long) = showService.getShowPosterData(showId, true)
}