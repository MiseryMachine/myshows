package com.rjs.myshows.common.util.security.exchange

import com.rjs.myshows.common.domain.security.UserCredsDto
import com.rjs.myshows.common.domain.security.UserDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod

class LoginExchange(credentials: UserCredsDto):
    RestExchange<UserCredsDto, UserDto>(
        "/users/login",
        object: ParameterizedTypeReference<UserDto>(){},
        HttpMethod.POST,
        null,
        credentials
    )