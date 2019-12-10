package com.ut.iot.rooms.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.viewholder.RoomItemViewHolder
import com.ut.iot.rooms.data.model.Room
import com.ut.iot.rooms.ui.home.booking.RoomItemClicked

/**
 * Created by Saeed on 07/12/2019.
 */
class RoomItemAdapter(private val items: List<Room>, private val roomItemClicked: RoomItemClicked) :
    RecyclerView.Adapter<RoomItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomItemViewHolder {
        return RoomItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.room_item,
                parent,
                false
            ), roomItemClicked
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RoomItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}