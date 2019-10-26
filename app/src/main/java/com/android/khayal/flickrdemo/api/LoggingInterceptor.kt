package com.android.khayal.flickrdemo.api

import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor() : Interceptor {
    lateinit var requestUrl: String

    override fun intercept(chain: Interceptor.Chain): Response {
        requestUrl = chain.request().url().toString()
        return chain.proceed(chain.request())
    }
}