package com.rjs.myshows.common.util.security

import com.rjs.myshows.common.util.security.exchange.RestExchange
import com.rjs.myshows.common.util.web.RestClient
import com.rjs.myshows.common.util.web.WebServiceException
import org.springframework.http.HttpStatus

class MyShowsRestClient(private val webServiceUrl: String) {
    private val restClient: RestClient = RestClient()

    fun <REQ, RES> exchange(restExchange: RestExchange<REQ, RES>): RES {
        val responseEntity = restClient.exchange(restExchange.httpMethod,
            webServiceUrl + restExchange.url,
            restExchange.user?.username ?: "",
            restExchange.user?.password ?: "",
            restExchange.requestObject,
            restExchange.typeReference,
            restExchange.uriParameters)

        if (responseEntity.statusCode != HttpStatus.OK) {
            throw WebServiceException(responseEntity.statusCode,
                "Response status code = ${responseEntity.statusCode.reasonPhrase}")
        }

        return responseEntity.body ?: throw WebServiceException(HttpStatus.NOT_FOUND, "Response body is null.")
    }
}