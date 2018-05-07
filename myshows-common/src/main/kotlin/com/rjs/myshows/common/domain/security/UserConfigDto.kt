package com.rjs.myshows.common.domain.security

import com.rjs.myshows.common.domain.UserDto
import java.time.LocalDate

class UserConfigDto: UserDto(), UserBase {
    override var username: String = ""
    override var email: String = ""
    override var firstName: String? = null
    override var lastName: String? = null
    override var enabled: Boolean = true
    override var roles: MutableSet<Role> = linkedSetOf()
    override var dateOfBirth: LocalDate? = null
    var password: String = ""
    var confirmPassword: String? = null
}