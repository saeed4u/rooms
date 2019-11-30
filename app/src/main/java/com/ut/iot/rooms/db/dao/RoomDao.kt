package com.ut.iot.rooms.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ut.iot.rooms.data.model.Room


/**
 * Created by Saeed on 27/11/2019.
 */
@Dao
interface RoomDao : BaseDao<Room> {

    @Query("SELECT * FROM rooms WHERE id = :id")
    fun selectRoom(id: Int): LiveData<Room>

    @Query("DELETE FROM rooms")
    fun deleteAll()
}