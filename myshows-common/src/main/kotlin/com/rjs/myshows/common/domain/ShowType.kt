package com.rjs.myshows.common.domain

interface ShowType: BaseElement {
    var name: String
    var genres: MutableSet<String>
    var ratings: MutableSet<String>
}