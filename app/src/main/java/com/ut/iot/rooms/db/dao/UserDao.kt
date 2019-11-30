package com.ut.iot.rooms.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ut.iot.rooms.data.model.User
import com.ut.iot.rooms.data.model.UserInfo


/**
 * Created by Saeed on 27/11/2019.
 */
@Dao
interface UserDao : BaseDao<User> {

    @Transaction
    @Query("SELECT * FROM user LIMIT 1")
    fun getLoggedInUser(): LiveData<UserInfo>

    @Query("DELETE FROM user")
    fun deleteAll()

}