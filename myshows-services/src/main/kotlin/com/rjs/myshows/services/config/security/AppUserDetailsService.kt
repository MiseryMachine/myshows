package com.rjs.myshows.services.config.security

import com.rjs.myshows.services.domain.UserEntity
import com.rjs.myshows.services.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserEntity = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("Username $username does not exist.")

        return UserPrincipal(user)
    }
}