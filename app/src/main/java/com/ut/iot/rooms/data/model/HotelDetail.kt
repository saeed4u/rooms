package com.ut.iot.rooms.data.model


/**
 * Created by Saeed on 02/12/2019.
 */

enum class HotelDetailType {
    TITLE, RATING, ROOM,PRICE
}

data class HotelDetail(val value: String, val hotelDetailType: HotelDetailType)