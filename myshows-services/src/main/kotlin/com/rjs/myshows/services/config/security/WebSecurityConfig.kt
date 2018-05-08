package com.rjs.myshows.services.config.security

import com.rjs.myshows.services.config.filter.CustomFilter
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val userDetailsService: AppUserDetailsService, private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint): WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/resources/**")
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder())

        return authProvider
    }

    fun encode(value: String): String = if (StringUtils.isNotBlank(value)) encoder().encode(value) else ""

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    override fun configure(http: HttpSecurity?) {
        http
            ?.csrf()?.disable()
            ?.authorizeRequests()
                ?.antMatchers("/ws/admin/**")?.hasRole("ADMIN")
                ?.antMatchers("/ws/shows/**", "/ws/users/config/**")?.hasRole("USER")
                ?.antMatchers("/ws/users/login", "/ws/users/create")?.permitAll()
                ?.anyRequest()?.authenticated()
            ?.and()
            ?.httpBasic()
            ?.authenticationEntryPoint(restAuthenticationEntryPoint)
        http?.addFilterAfter(CustomFilter(), BasicAuthenticationFilter::class.java)
/*
            ?.formLogin()
                ?.loginPage("/user/login")?.permitAll()
            ?.and()
            ?.logout()
                ?.deleteCookies("JSESSIONID")
                ?.clearAuthentication(true)
                ?.invalidateHttpSession(true)?.permitAll()
                ?.logoutSuccessUrl("/home")
*/
    }
}