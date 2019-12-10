package com.ut.iot.rooms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ut.iot.rooms.data.model.*
import com.ut.iot.rooms.db.dao.*
import com.ut.iot.rooms.db.typeconverter.RoomsDBConverter


/**
 * Created by Saeed on 27/11/2019.
 */
@Database(
    version = 4,
    entities = [User::class, Booking::class, Room::class, RoomType::class, RoomImage::class, Hotel::class]
)
@TypeConverters(value = [RoomsDBConverter::class])
abstract class RoomsDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bookingDao(): BookingDao

    abstract fun roomDao(): RoomDao

    abstract fun roomTypeDao(): RoomTypeDao

    abstract fun imageDao(): ImageDao

    abstract fun hotelDao(): HotelDao

}