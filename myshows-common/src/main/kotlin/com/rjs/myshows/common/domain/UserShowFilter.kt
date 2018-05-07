package com.rjs.myshows.common.domain

interface UserShowFilter: BaseElement {
//    var user: User
    var name: String
    var showTypeName: String
    var title: String
    var starRating: Int
    var format: String
    var genres: MutableSet<String>
}