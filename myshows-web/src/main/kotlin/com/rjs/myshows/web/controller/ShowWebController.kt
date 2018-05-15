package com.rjs.myshows.web.controller

import com.rjs.myshows.common.domain.*
import com.rjs.myshows.common.util.security.MyShowsRestClient
import com.rjs.myshows.common.util.security.exchange.ShowTypeExchange
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/shows")
class ShowWebController(myShowsRestClient: MyShowsRestClient, private val showTypeExchange: ShowTypeExchange): RestClientController(myShowsRestClient) {
    private var showTypes: MutableList<ShowTypeDto> = mutableListOf()

    @GetMapping("/search")
    fun getSearchForm(@ModelAttribute("showSearchFilter") showFilterDto: ShowFilterDto, @ModelAttribute("userFilters") userShowFilters: MutableList<UserShowFilterDto>): ModelAndView {
        val mav = ModelAndView("/shows/search")

        mav.model.putAll(buildSearchModel(showFilterDto, userShowFilters))

        return mav
    }

    private fun buildSearchModel(
        showFilterDto: ShowFilterDto,
        userShowFilters: MutableList<UserShowFilterDto>,
        selUserFilter: String? = null,
        searchResults: MutableList<ShowDto> = mutableListOf()) : Map<String, Any?> {

        if (showTypes.isEmpty()) {
            showTypes = myShowsRestClient.exchange(showTypeExchange.getAllExchange())
        }

        val model: MutableMap<String, Any?> = hashMapOf()
        model["showTypes"] = showTypes
        model["starRatings"] = starRatings
        model["mediaFormats"] = mediaFormats
        model["userFilters"] = userShowFilters
        model["selUserFilter"] = selUserFilter
        model["showSearchFilter"] = showFilterDto
        model["numResults"] = searchResults.size
        model["shows"] = searchResults

        return model
    }
}