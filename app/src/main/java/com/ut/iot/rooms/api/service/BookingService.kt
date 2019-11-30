package com.ut.iot.rooms.api.service

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.booking.BookingResponse
import retrofit2.http.GET


/**
 * Created by Saeed on 28/11/2019.
 */
interface BookingService {

    @GET("user/bookings")
    fun getUserBookings(): LiveData<ApiResponse<BookingResponse>>

}