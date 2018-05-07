package com.rjs.myshows.domain.security

class UserAuthDto: UserBase {
    override var username: String = ""
    var password: String = ""
}