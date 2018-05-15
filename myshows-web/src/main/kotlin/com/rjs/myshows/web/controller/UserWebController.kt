package com.rjs.myshows.web.controller

import com.rjs.myshows.common.util.security.MyShowsRestClient
import com.rjs.myshows.common.util.security.exchange.UserExchange
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserWebController(myShowsRestClient: MyShowsRestClient, private val userExchange: UserExchange): RestClientController(myShowsRestClient) {

}