package com.rjs.myshows.common.domain.security

class UserAuthDto: UserBase {
    override var username: String = ""
    var password: String = ""
}