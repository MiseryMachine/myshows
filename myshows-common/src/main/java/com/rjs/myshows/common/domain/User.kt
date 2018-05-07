package com.rjs.myshows.domain

import com.rjs.myshows.domain.security.Role
import java.time.LocalDate

interface User: BaseElement {
//    var username: String
    var email: String
    var firstName: String?
    var lastName: String?
    var enabled: Boolean
    var roles: MutableSet<Role>
    var dateOfBirth: LocalDate?
}