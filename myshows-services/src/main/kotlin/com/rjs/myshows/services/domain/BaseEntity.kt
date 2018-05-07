package com.rjs.myshows.services.domain

import com.rjs.myshows.common.domain.BaseElement
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity: BaseElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long? = null

    fun uuid() = this.javaClass.name + ":" + id

    override fun toString(): String {
        return uuid()
    }
}