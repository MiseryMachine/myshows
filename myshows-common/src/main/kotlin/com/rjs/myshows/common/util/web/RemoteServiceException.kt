package com.rjs.myshows.common.util.web

import java.io.IOException

open class RemoteServiceException(message: String?  = "", cuase: Throwable? = null): IOException(message, cuase)