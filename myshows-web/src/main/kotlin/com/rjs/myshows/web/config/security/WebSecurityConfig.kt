package com.rjs.myshows.web.config.security

import com.rjs.myshows.common.util.security.MyShowsRestClient
import com.rjs.myshows.common.util.security.RestAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@ConfigurationProperties("my-shows")
class WebSecurityConfig: WebSecurityConfigurerAdapter() {
    private var webServiceUrl = ""

    @Bean
    fun myshowsRestClient() = MyShowsRestClient(webServiceUrl)

    @Bean
    fun authenticationProvider() = RestAuthenticationProvider(myshowsRestClient())

    @Bean
    fun encoder() = BCryptPasswordEncoder(11)

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    fun getWebServiceUrl() = webServiceUrl
    fun setWebServiceUrl(prop: String) {
        webServiceUrl = prop
    }

    override fun configure(http: HttpSecurity?) {
        http
            ?.csrf()?.disable()
            ?.authorizeRequests()
            ?.antMatchers("/admin/**")?.hasRole("ADMIN")
            //                ?.antMatchers("/rest/**")?.hasRole("REST_USER")
            ?.antMatchers("/shows/**")?.hasRole("USER")
            ?.antMatchers("/", "/home", "/user/signup", "/user/registration", "/ws/**", "/webjars/**", "/css/**", "/img/**", "/js/**", "/datatables/**")?.permitAll()
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.formLogin()
            ?.loginPage("/user/login")?.permitAll()
            //                ?.loginPage("/login")?.permitAll()
            //                ?.successForwardUrl("/home")
            //                ?.failureForwardUrl("/user/login")
            ?.and()
            ?.logout()
            ?.deleteCookies("JESSSIONID")
            ?.clearAuthentication(true)
            ?.invalidateHttpSession(true)
            ?.permitAll()
            ?.logoutSuccessUrl("/home")
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/resources/**")
    }
}