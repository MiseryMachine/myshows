package com.rjs.myshows.services.domain

import com.rjs.myshows.common.domain.User
import com.rjs.myshows.common.domain.security.Role
import com.rjs.myshows.common.domain.security.UserBase
import java.time.LocalDate
import javax.persistence.*

@Entity
class UserEntity: BaseEntity(), UserBase, User {
    @Column(length = 40, nullable = false, unique = true)
    override var username: String = ""

    @Column(nullable = false)
    var password: String = ""

    @Column(nullable = false, unique = true)
    override var email: String = ""

    @Column(name = "first_name", length = 40)
    override var firstName: String? = null

    @Column(name = "last_name", length = 40)
    override var lastName: String? = null
    override var enabled: Boolean = true

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [(JoinColumn(name = "user_id"))])
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    override var roles: MutableSet<Role> = linkedSetOf()

    @Column(name = "dob")
    override var dateOfBirth: LocalDate? = null
}