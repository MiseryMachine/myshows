package com.rjs.myshows.services.service

import com.rjs.myshows.common.domain.security.Role
import com.rjs.myshows.common.domain.security.UserConfigDto
import com.rjs.myshows.services.config.security.WebSecurityConfig
import com.rjs.myshows.services.domain.UserEntity
import com.rjs.myshows.services.repository.PageRequestBuilder
import com.rjs.myshows.services.repository.UserRepository
import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

@Service("userService")
@Transactional
class UserService(
    val userRepository: UserRepository,
    val securityConfig: WebSecurityConfig
) : BaseService<UserEntity, UserRepository>(userRepository) {
    val defaultStringMatcher = ExampleMatcher.matching()
        .withMatcher("username", ExampleMatcher.GenericPropertyMatcher().ignoreCase())
        .withMatcher("username", ExampleMatcher.GenericPropertyMatcher().contains())

    // Gets
    fun getUsers(): List<UserEntity> = userRepository.findAll()
    fun getUsersPaged(exampleUser: UserEntity): Page<UserEntity> = getUsersPaged(exampleUser, 0, 10)
    fun getUsersPaged(exampleUser: UserEntity?, pageNumber: Int, pageSize: Int): Page<UserEntity> {
        val pageRequest = PageRequestBuilder("username").pageNumber(pageNumber).pageSize(pageSize).pageRequest()

        return if (exampleUser == null) userRepository.findAll(pageRequest)
        else userRepository.findAll(Example.of(exampleUser), pageRequest)
    }

    // Finds
    fun findByUsername(username: String): UserEntity? = userRepository.findByUsername(username)
    fun findByUsernameAndPassword(username: String,  password: String): UserEntity? = userRepository.findByUsernameAndPassword(username, password)
    fun findByEmail(email: String): UserEntity? = userRepository.findByEmail(email)

    fun userExists(username: String) = findByUsername(username) != null

//    fun login(username: String, password: String): User =
//        userRepository.findByUsernameAndPassword(username, password) ?: throw Exception("Invalid username or password.")

    fun hasRoles(request: HttpServletRequest, vararg roleNames: String): Boolean {
        for (roleName in roleNames) {
            if (request.isUserInRole(roleName)) {
                return true
            }
        }

        return false
    }

    // Creates/Saves
    @Transactional
    fun createUser(userConfigDto: UserConfigDto): UserEntity? {
        if (findByUsername(userConfigDto.username) == null) {
            val user = UserEntity()
            user.username = userConfigDto.username

            return saveUser(user, userConfigDto)
        }

        return null
    }

    @Transactional
    fun updateUser(userConfigDto: UserConfigDto): UserEntity? {
        val user = findByUsername(userConfigDto.username)

        return if (user != null) saveUser(user, userConfigDto) else null
    }

    private fun saveUser(user: UserEntity, userConfigDto: UserConfigDto): UserEntity {
        user.firstName = userConfigDto.firstName
        user.lastName = userConfigDto.lastName
        user.email = userConfigDto.email
        user.roles.add(Role.ROLE_USER)

        val pw = userConfigDto.password

        if (StringUtils.isNotBlank(pw)) {
            user.password = securityConfig.encode(pw)
        }

        return save(user)
    }
}