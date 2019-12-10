package com.ut.iot.rooms.api.model.booking

import com.ut.iot.rooms.data.model.Booking


/**
 * Created by Saeed on 28/11/2019.
 */
data class BookingsResponse (val bookings: List<Booking>)

data class BookingResponse(val booking: Booking)