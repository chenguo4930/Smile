package com.yhao.commen.download

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ProgressInterceptor(private val progressListener: ProgressListener) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder().body(ProgressResponseBody(originalResponse.body()!!, progressListener)).build()
    }
}