package com.rjs.myshows.services.controller

import com.rjs.myshows.common.domain.UserDto
import com.rjs.myshows.services.domain.UserEntity
import com.rjs.myshows.services.service.UserService
import org.modelmapper.ModelMapper

abstract class UserController(
    protected val userService: UserService,
    protected val modelMapper: ModelMapper
) {
    protected fun convertToUser(userDto: UserDto, user: UserEntity) = modelMapper.map(userDto, user)
    protected fun convertToUserDto(user: UserEntity): UserDto = modelMapper.map(user, UserDto::class.java)
}
