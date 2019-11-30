package com.ut.iot.rooms.api.service

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.BaseResponse
import com.ut.iot.rooms.api.model.auth.AuthRequest
import com.ut.iot.rooms.api.model.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by Saeed on 2019-10-18.
 */
interface AuthService {

    @POST("auth/login")
    fun login(@Body authRequest: AuthRequest): LiveData<ApiResponse<AuthResponse>>

    @POST("auth/register")
    fun register(@Body authRequest: AuthRequest): LiveData<ApiResponse<AuthResponse>>

    @POST("auth/logout")
    fun logout(): LiveData<ApiResponse<BaseResponse>>

}