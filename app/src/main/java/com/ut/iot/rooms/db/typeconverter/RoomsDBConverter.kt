package com.ut.iot.rooms.db.typeconverter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.ut.iot.rooms.data.model.*


/**
 * Created by Saeed on 2019-06-21.
 */
class RoomsDBConverter {

    var moshi: Moshi = Moshi.Builder().build()


    @TypeConverter
    fun fromCountry(country: Country?): String {
        return moshi.adapter(Country::class.java).toJson(country)
    }

    @TypeConverter
    fun toCountry(country: String): Country? {
        return moshi.adapter(Country::class.java).fromJson(country)
    }

    @TypeConverter
    fun fromRoomType(roomType: RoomType?): String {
        return moshi.adapter(RoomType::class.java).toJson(roomType)
    }

    @TypeConverter
    fun toRoomType(roomType: String): RoomType? {
        return moshi.adapter(RoomType::class.java).fromJson(roomType)
    }

    @TypeConverter
    fun fromRoom(room: Room?): String {
        return moshi.adapter(Room::class.java).toJson(room)
    }

    @TypeConverter
    fun toRoom(room: String): Room? {
        return moshi.adapter(Room::class.java).fromJson(room)
    }

    @TypeConverter
    fun fromRooms(rooms: List<Room>): String {
        val type = Types.newParameterizedType(List::class.java, Room::class.java)
        val adapter = moshi.adapter<List<Room>>(type)
        return adapter.toJson(rooms)
    }

    @TypeConverter
    fun toRooms(rooms: String): List<Room>? {
        val type = Types.newParameterizedType(List::class.java, Room::class.java)
        val adapter = moshi.adapter<List<Room>>(type)
        return adapter.fromJson(rooms)
    }


    @TypeConverter
    fun fromRoomImages(roomImages: List<RoomImage>): String {
        val type = Types.newParameterizedType(List::class.java, RoomImage::class.java)
        val adapter = moshi.adapter<List<RoomImage>>(type)
        return adapter.toJson(roomImages)
    }

    @TypeConverter
    fun toRoomImages(roomImages: String): List<RoomImage>? {
        val type = Types.newParameterizedType(List::class.java, RoomImage::class.java)
        val adapter = moshi.adapter<List<RoomImage>>(type)
        return adapter.fromJson(roomImages)
    }

    @TypeConverter
    fun fromImages(images: List<Image>): String {
        val type = Types.newParameterizedType(List::class.java, Image::class.java)
        val adapter = moshi.adapter<List<Image>>(type)
        return adapter.toJson(images)
    }

    @TypeConverter
    fun toImages(images: String): List<Image>? {
        val type = Types.newParameterizedType(List::class.java, Image::class.java)
        val adapter = moshi.adapter<List<Image>>(type)
        return adapter.fromJson(images)
    }


}