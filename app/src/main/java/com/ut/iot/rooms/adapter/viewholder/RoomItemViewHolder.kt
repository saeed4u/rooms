package com.ut.iot.rooms.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ut.iot.rooms.data.model.Room
import com.ut.iot.rooms.ui.home.booking.RoomItemClicked
import kotlinx.android.synthetic.main.room_item.view.*

/**
 * Created by Saeed on 07/12/2019.
 */
class RoomItemViewHolder constructor(
    private val view: View,
    private val roomItemClicked: RoomItemClicked
) : RecyclerView.ViewHolder(view) {

    fun bind(room: Room) {
        room.images?.first()?.apply {
            Picasso.get()
                .load(src)
                .into(view.room_image)
        }
        view.room_name.text = room.name
        view.setOnClickListener {
            roomItemClicked.onItemClicked(room)
        }
        if (room.selected) {
            view.selected.visibility = View.VISIBLE
        } else {
            view.selected.visibility = View.GONE
        }
    }

}