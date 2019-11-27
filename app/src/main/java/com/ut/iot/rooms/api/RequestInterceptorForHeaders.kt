package com.ut.iot.rooms.api

import com.ut.iot.rooms.AUTHORIZATION_HEADER
import com.ut.iot.rooms.DEVICE_ID_HEADER
import com.ut.iot.rooms.state.StateManager
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber


/**
 * Created by Saeed on 2019-06-20.
 */
class RequestInterceptorForHeaders(private val stateManager: StateManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val headers = originalRequest.headers()

        Timber.d("Interceptor called")
        Timber.d("Device id ${stateManager.getDeviceId()}")
        Timber.d("Auth token ${stateManager.getAuthToken()}")

        val requestBuilder = originalRequest.newBuilder()
            .headers(
                headers
                    .newBuilder()
                    .add(DEVICE_ID_HEADER, stateManager.getDeviceId().toString())
                    .add(AUTHORIZATION_HEADER, "Bearer ${stateManager.getAuthToken()}")
                    .build()
            )
        return chain.proceed(requestBuilder.build())
    }
}