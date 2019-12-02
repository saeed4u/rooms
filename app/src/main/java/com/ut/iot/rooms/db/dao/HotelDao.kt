package com.ut.iot.rooms.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ut.iot.rooms.data.model.Hotel


/**
 * Created by Saeed on 27/11/2019.
 */
@Dao
interface HotelDao: BaseDao<Hotel> {

    @Query("SELECT * FROM hotels WHERE id= :hotelId")
    fun getHotel(hotelId: Int): LiveData<Hotel>

    @Query("SELECT * FROM hotels")
    fun getHotels(): LiveData<List<Hotel>>

    @Query("DELETE FROM hotels")
    fun deleteAll()
}