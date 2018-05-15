package com.rjs.myshows.web.controller

import com.rjs.myshows.common.util.security.MyShowsRestClient

abstract class RestClientController(protected val myShowsRestClient: MyShowsRestClient)