package com.rjs.myshows.domain

interface ShowType: BaseElement {
    var name: String
    var genres: MutableSet<String>
    var ratings: MutableSet<String>
}