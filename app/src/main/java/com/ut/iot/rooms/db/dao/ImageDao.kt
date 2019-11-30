package com.ut.iot.rooms.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ut.iot.rooms.data.model.RoomImage


/**
 * Created by Saeed on 27/11/2019.
 */
@Dao
interface ImageDao : BaseDao<RoomImage> {

    @Query("SELECT * FROM room_images WHERE id = :id")
    fun selectRoomImage(id: Int): LiveData<RoomImage>

    @Query("DELETE FROM room_images")
    fun deleteAll()
}