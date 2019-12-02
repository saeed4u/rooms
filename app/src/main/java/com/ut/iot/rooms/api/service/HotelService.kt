package com.ut.iot.rooms.api.service

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.hotel.HotelResponse
import retrofit2.http.GET


/**
 * Created by Saeed on 30/11/2019.
 */
interface HotelService {

    @GET("hotels")
    fun getHotels():LiveData<ApiResponse<HotelResponse>>

}