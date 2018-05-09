package com.rjs.myshows.common.util.security

import com.rjs.myshows.common.domain.security.UserDto
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MyShowsUserDetails(val userDto: UserDto): UserDetails {
    override fun getAuthorities() = userDto.roles.map { role -> SimpleGrantedAuthority(role.name) }.toMutableList()

    override fun isEnabled() = userDto.enabled

    override fun getUsername() = userDto.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = userDto.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}