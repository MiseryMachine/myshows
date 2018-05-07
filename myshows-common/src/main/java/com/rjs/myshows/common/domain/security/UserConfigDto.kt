package com.rjs.myshows.domain.security

import com.rjs.myshows.domain.BaseDto
import com.rjs.myshows.domain.User
import java.time.LocalDate

class UserConfigDto: BaseDto(), UserBase, User {
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