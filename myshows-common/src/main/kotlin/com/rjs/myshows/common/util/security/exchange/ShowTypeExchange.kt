package com.rjs.myshows.common.util.security.exchange

import com.rjs.myshows.common.domain.ShowTypeDto
import org.springframework.core.ParameterizedTypeReference

class ShowTypeExchange {
    fun getAllExchange() = RestExchange<String, MutableList<ShowTypeDto>>("", object: ParameterizedTypeReference<MutableList<ShowTypeDto>>(){})
}