package com.ut.iot.rooms.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ut.iot.rooms.data.model.RoomType


/**
 * Created by Saeed on 27/11/2019.
 */
@Dao
interface RoomTypeDao : BaseDao<RoomType> {

    @Query("SELECT * FROM room_types WHERE id = :id")
    fun selectRoomType(id: Int): LiveData<RoomType>

    @Query("DELETE FROM room_types")
    fun deleteAll()

}