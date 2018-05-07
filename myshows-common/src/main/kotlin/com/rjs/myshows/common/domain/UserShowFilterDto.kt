package com.rjs.myshows.common.domain

class UserShowFilterDto: BaseDto(), UserShowFilter {
    override var name: String = ""
    override var showTypeName: String = ""
    override var title: String = ""
    override var starRating: Int = 0
    override var format: String = ""
    override var genres: MutableSet<String> = linkedSetOf()
}