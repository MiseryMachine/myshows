package com.rjs.myshows.domain

class ShowTypeDto: BaseDto(), ShowType {
    override var name: String = ""
    override var genres: MutableSet<String> = mutableSetOf()
    override var ratings: MutableSet<String> = mutableSetOf()
}