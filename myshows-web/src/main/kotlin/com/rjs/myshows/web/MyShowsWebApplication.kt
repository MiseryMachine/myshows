package com.rjs.myshows.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyShowsWebApplication

fun main(args: Array<String>) {
    runApplication<MyShowsWebApplication>(*args)
}
