package com.rjs.myshows.web.config

import nz.net.ultraq.thymeleaf.LayoutDialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {
    @Bean
    fun byteArrayHttpMessageConverter(): ByteArrayHttpMessageConverter {
        val arrayHttpMessageConverter = ByteArrayHttpMessageConverter()
        arrayHttpMessageConverter.supportedMediaTypes = mutableListOf(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG)

        return arrayHttpMessageConverter
    }

    @Bean
    fun layoutDialect() = LayoutDialect()

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/home").setViewName("home")
        registry.addViewController("/").setViewName("home")
        registry.addViewController("/user/login").setViewName("user/login")
        registry.addViewController("/user/signup").setViewName("user/signup")
        registry.addViewController("/shows/search").setViewName("shows/search")
        registry.addViewController("/admin/edit-show").setViewName("admin/edit-show")
        registry.addViewController("/admin/adminTemplate").setViewName("admin/adminTemplate")
        registry.addViewController("/admin/adminUsers").setViewName("admin/adminUsers")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("/webjars/**", "/css/**", "/img/**", "/js/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/", "classpath:/static/css/", "classpath:/static/img/", "classpath:/static/js/")
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(byteArrayHttpMessageConverter())
    }
}