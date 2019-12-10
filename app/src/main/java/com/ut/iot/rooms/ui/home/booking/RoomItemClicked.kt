package com.ut.iot.rooms.ui.home.booking

import com.ut.iot.rooms.data.model.Room


/**
 * Created by Saeed on 30/11/2019.
 */
interface RoomItemClicked {

    fun onItemClicked(room: Room)

}