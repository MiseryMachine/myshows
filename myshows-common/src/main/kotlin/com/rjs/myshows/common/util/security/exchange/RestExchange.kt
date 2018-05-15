package com.rjs.myshows.common.util.security.exchange

import com.rjs.myshows.common.domain.security.UserCredsDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod

class RestExchange<out REQ, RES>(
    val url: String = "",
    val typeReference: ParameterizedTypeReference<RES>,
    val httpMethod: HttpMethod = HttpMethod.GET,
    val user: UserCredsDto? = null,
    val requestObject: REQ? = null,
    val uriParameters: Map<String, String> = mapOf())