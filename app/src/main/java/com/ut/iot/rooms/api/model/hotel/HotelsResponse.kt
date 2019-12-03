package com.ut.iot.rooms.api.model.hotel

import com.ut.iot.rooms.data.model.Hotel


/**
 * Created by Saeed on 30/11/2019.
 */
data class HotelsResponse(val hotels: List<Hotel>)

data class HotelResponse(val hotel: Hotel)