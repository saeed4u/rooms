package com.ut.iot.rooms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ut.iot.rooms.data.model.*
import com.ut.iot.rooms.db.dao.*


/**
 * Created by Saeed on 27/11/2019.
 */
@Database(
    version = 2,
    entities = [User::class, Booking::class, Room::class, RoomType::class, RoomImage::class]
)
abstract class RoomsDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bookingDao(): BookingDao

    abstract fun roomDao(): RoomDao

    abstract fun roomTypeDao(): RoomTypeDao

    abstract fun imageDao(): ImageDao

}