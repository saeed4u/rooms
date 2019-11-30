package com.ut.iot.rooms.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


/**
 * Created by Saeed on 27/11/2019.
 */
@Entity
data class User(
    @PrimaryKey val id: Int, val name: String, val email: String, val token: String,
    @Ignore val bookings: List<Booking>
) {
    constructor(id: Int, name: String, token: String, email: String) : this(
        id,
        name,
        email,
        token,
        emptyList()
    )
}