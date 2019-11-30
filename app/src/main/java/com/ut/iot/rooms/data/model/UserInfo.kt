package com.ut.iot.rooms.data.model

import androidx.room.Embedded
import androidx.room.Relation


/**
 * Created by Saeed on 27/11/2019.
 */
class UserInfo {

    @Embedded
    var user: User? = null


    @Relation(parentColumn = "id", entityColumn = "user_id")
    var bookings: Set<Booking> = setOf()

}