package com.rjs.myshows.services.controller.rest

import org.springframework.http.HttpStatus

class WebApplicationException(
    val status: HttpStatus,
    message: String
): Exception(message)