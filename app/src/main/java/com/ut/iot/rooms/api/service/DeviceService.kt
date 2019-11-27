package com.ut.iot.rooms.api.service

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.device.DeviceRequest
import com.ut.iot.rooms.api.model.device.DeviceResponse
import com.ut.iot.rooms.api.model.device.UpdateFCMToken
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by Saeed on 2019-10-18.
 */
interface DeviceService {

    @POST("device/register")
    fun registerDevice(@Body deviceRequest: DeviceRequest): LiveData<ApiResponse<DeviceResponse>>

    @POST("device/update-fcm-token")
    fun updateFCMToken(@Body updateFCMToken: UpdateFCMToken):
            LiveData<ApiResponse<DeviceResponse>>
}