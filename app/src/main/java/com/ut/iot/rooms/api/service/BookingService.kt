package com.ut.iot.rooms.api.service

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.booking.BookingResponse
import com.ut.iot.rooms.api.model.booking.BookingsResponse
import com.ut.iot.rooms.data.model.AddBookingRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by Saeed on 28/11/2019.
 */
interface BookingService {

    @GET("user/bookings")
    fun getUserBookings(): LiveData<ApiResponse<BookingsResponse>>

    @POST("user/bookings")
    fun addBooking(@Body bookingRequest: AddBookingRequest): LiveData<ApiResponse<BookingResponse>>

}