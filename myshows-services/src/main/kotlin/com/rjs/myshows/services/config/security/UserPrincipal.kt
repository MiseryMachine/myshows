package com.rjs.myshows.services.config.security

import com.rjs.myshows.services.domain.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(private val user: UserEntity): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = mutableListOf()

        for (role in user.roles) {
            authorities.add(SimpleGrantedAuthority(role.name))
        }

        return authorities
    }

    override fun isEnabled() = user.enabled

    override fun getUsername() = user.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = user.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}