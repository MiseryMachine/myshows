package com.rjs.myshows.common.domain.security

import com.rjs.myshows.common.domain.BaseElement
import java.time.LocalDate

interface User: BaseElement {
    var username: String
    var password: String
    var email: String
    var firstName: String?
    var lastName: String?
    var enabled: Boolean
    var roles: MutableSet<Role>
    var dateOfBirth: LocalDate?
}