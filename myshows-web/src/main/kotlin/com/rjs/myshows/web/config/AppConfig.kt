package com.rjs.myshows.web.config

import com.rjs.myshows.common.util.security.exchange.ShowTypeExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat

@Configuration
class AppConfig {
    @Bean
    fun dateFormat() = SimpleDateFormat("yyyy-MM-dd")

    @Bean
    fun showTypeExchange() = ShowTypeExchange()
}