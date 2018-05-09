package com.rjs.myshows.common.domain.security

import com.rjs.myshows.common.domain.BaseDto
import java.time.LocalDate

open class UserDto: BaseDto(), User {
    override var username: String = ""
    override var password: String = ""
    override var email: String = ""
    override var firstName: String? = null
    override var lastName: String? = null
    override var enabled: Boolean = true
    override var roles: MutableSet<Role> = linkedSetOf()
    override var dateOfBirth: LocalDate? = null
}