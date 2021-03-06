package com.ut.iot.rooms.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


/**
 * Created by Saeed on 2019-06-20.
 */
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg data: T)

    @Update
    suspend fun update(vararg data: T)

}