package com.rjs.myshows.domain

abstract class BaseDto: BaseElement {
    override var id: Long? = null

    fun uuid() = this.javaClass.name + ":" + (id ?: "NULL")

    override fun toString(): String {
        return uuid()
    }
}