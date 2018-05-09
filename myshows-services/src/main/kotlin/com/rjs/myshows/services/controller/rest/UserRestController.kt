package com.rjs.myshows.services.controller.rest

import com.rjs.myshows.common.domain.security.UserConfigDto
import com.rjs.myshows.common.domain.security.UserCredsDto
import com.rjs.myshows.common.domain.security.UserDto
import com.rjs.myshows.services.controller.UserController
import com.rjs.myshows.services.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ws/users")
class UserRestController(
    userService: UserService,
    modelMapper: ModelMapper
): UserController(userService, modelMapper) {
    @PostMapping("/login")
    fun login(@RequestBody userCredsDto: UserCredsDto): UserDto {
        val user = userService.findByUsernameAndPassword(userCredsDto.username, userCredsDto.password) ?:
            throw WebApplicationException(HttpStatus.UNAUTHORIZED, "User login failed.")

        return convertToUserDto(user)
    }

    @PostMapping("/create")
    fun createUser(@RequestBody userConfigDto: UserConfigDto): UserDto {
        if (userConfigDto.password != userConfigDto.confirmPassword) {
            throw WebApplicationException(HttpStatus.PRECONDITION_FAILED, "Passwords do not match.")
        }

        if (userService.findByUsername(userConfigDto.username) != null) {
            throw WebApplicationException(HttpStatus.CONFLICT, "User with name ${userConfigDto.username} already exists.")
        }

        val user = userService.createUser(userConfigDto) ?: throw WebApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Error occurred while crating user ${userConfigDto.username}")

        return convertToUserDto(user)
    }

    @PostMapping("/config/password-change")
    fun changePassword(@RequestBody userConfigDto: UserConfigDto): UserDto {
        var user = userService.findByUsername(userConfigDto.username) ?: throw WebApplicationException(HttpStatus.NOT_FOUND, "User not found.")

        user.password = userConfigDto.password
        user = userService.save(user)

        return convertToUserDto(user)
    }

    @PostMapping("/config/update")
    fun saveUser(@RequestBody userConfigDto: UserConfigDto): UserDto {
        var user = userService.findByUsername(userConfigDto.username) ?: throw WebApplicationException(HttpStatus.NOT_FOUND, "User not found.")

        // This should apply only the allowable changes
        convertToUser(userConfigDto, user)

        user = userService.save(user)

        return convertToUserDto(user)
    }
}