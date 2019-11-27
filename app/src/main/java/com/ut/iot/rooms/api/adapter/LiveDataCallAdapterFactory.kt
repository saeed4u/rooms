package com.ut.iot.rooms.api.adapter

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * Created by Saeed on 2019-10-18.
 * An adapter factory that creates a LiveDataCallAdapter
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val parameterType =
            getParameterUpperBound(0, returnType as ParameterizedType) as? ParameterizedType
                ?: throw IllegalArgumentException("Return type not parameterized")
        val rawType = getRawType(parameterType)
        require(rawType == ApiResponse::class.java) { "Type must be a ApiResponse" }
        val body = getParameterUpperBound(0, parameterType)
        return LiveDataCallAdapter<Type>(body)
    }
}