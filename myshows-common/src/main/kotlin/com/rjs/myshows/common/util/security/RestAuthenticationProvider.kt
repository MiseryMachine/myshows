package com.rjs.myshows.common.util.security

import com.rjs.myshows.common.domain.security.UserCredsDto
import com.rjs.myshows.common.util.security.exchange.UserExchange
import com.rjs.myshows.common.util.web.WebServiceException
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails

class RestAuthenticationProvider(private var myShowsRestClient: MyShowsRestClient, private val userExchange: UserExchange): AbstractUserDetailsAuthenticationProvider() {
    override fun doAfterPropertiesSet() {
//        super.doAfterPropertiesSet()
    }

    override fun retrieveUser(username: String?, auth: UsernamePasswordAuthenticationToken?): UserDetails? {
        val pw = auth?.credentials.toString()
        val userAuth = UserCredsDto()
        userAuth.username = username ?: ""
        userAuth.password = pw
        val loadedUser: UserDetails?

        try {
            // Authenticate user through web service
            val userDto = myShowsRestClient.exchange(userExchange.login(userAuth))
            loadedUser = MyShowsUserDetails(userDto)
        }
        catch (e: Exception) {
            if (e is WebServiceException) {
                throw AuthenticationServiceException("Authentication failed: [${e.status.value()} - ${e.status.reasonPhrase}]: ${e.message}", e)
            }

            throw AuthenticationServiceException("Authentication failed: ${e.message}", e)
        }

        return loadedUser
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, auth: UsernamePasswordAuthenticationToken?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}