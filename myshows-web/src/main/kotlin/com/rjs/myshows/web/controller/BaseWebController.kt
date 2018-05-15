package com.rjs.myshows.web.controller

import com.rjs.myshows.common.util.security.MyShowsRestClient
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class BaseWebController(myShowsRestClient: MyShowsRestClient): RestClientController(myShowsRestClient) {
    @GetMapping("/home")
    fun getHome() = "home"

    @PostMapping("/home")
    fun postHome() = "home"
}