package com.ut.iot.rooms.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey


/**
 * Created by Saeed on 27/11/2019.
 */

data class BookingItem(var booking: Booking? = null, var sectionTitle: String? = null)

data class Country(val name: String, val code: String)

data class Image(val src: String)

data class AddBookingRequest(val user_id: Int, val start_date: String, val end_date: String, val room_id: Int)

@Entity(tableName = "hotels")
data class Hotel(
    @PrimaryKey val id: Int, val name: String,
    val email: String,
    val address: String,
    val city: String,
    val state: String,
    val country: Country,
    val zip_code: String,
    val phone: String,
    val images: List<Image>,
    val rooms: List<Room>
    )

@Entity(tableName = "bookings")
@ForeignKey(
    entity = Room::class, parentColumns = ["id"], childColumns = ["room_id"],
    onDelete = ForeignKey.CASCADE
)
data class Booking(
    @PrimaryKey
    val id: Int,
    val total_nights: Int,
    val total_price: String,
    val currency: String,
    val hotel: String,
    val start_date: String,
    val end_date: String,
    var room_id: Int,
    var user_id: Int,
    var room: Room?
)

@Entity(tableName = "room_types")
data class RoomType(@PrimaryKey val id: Int, val name: String, var price: String = "")

@Entity(tableName = "room_images")
@ForeignKey(
    entity = Room::class, parentColumns = ["id"], childColumns = ["room_id"],
    onDelete = ForeignKey.CASCADE
)
data class RoomImage(@PrimaryKey val id: Int, val src: String, var room_id: Int)

@Entity(tableName = "rooms")
@ForeignKey(
    entity = RoomType::class, parentColumns = ["id"], childColumns = ["type_id"],
    onDelete = ForeignKey.CASCADE
)
data class Room(
    @PrimaryKey val id: Int,
    val name: String,
    var created_at: String?,
    var updated_at: String?,
    var type_id: Int?,
    var type: RoomType?,
    var images: MutableList<RoomImage>?,
    @Ignore var selected: Boolean = false
) {
    constructor(
        id: Int,
        name: String,
        created_at: String,
        updated_at: String,
        type_id: Int?
    ) : this(id, name, created_at, updated_at, type_id, null, null, false)
}