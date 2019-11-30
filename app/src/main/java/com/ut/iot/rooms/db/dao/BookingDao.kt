package com.ut.iot.rooms.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ut.iot.rooms.data.model.Booking


/**
 * Created by Saeed on 27/11/2019.
 */
@Dao
interface BookingDao: BaseDao<Booking> {

    @Query("SELECT * FROM bookings WHERE id= :bookingId")
    fun getBooking(bookingId: Int): LiveData<Booking>

    @Query("SELECT * FROM bookings WHERE user_id= :userId")
    fun getUserBookings(userId: Int): LiveData<List<Booking>>

    @Query("DELETE FROM bookings")
    fun deleteAll()
}