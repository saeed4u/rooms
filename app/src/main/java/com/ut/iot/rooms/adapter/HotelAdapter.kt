package com.ut.iot.rooms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.viewholder.HotelViewHolder
import com.ut.iot.rooms.data.model.Hotel
import com.ut.iot.rooms.ui.home.hotel.HotelItemClickListener

/**
 * Created by Saeed on 30/11/2019.
 */
class HotelAdapter(
    private val items: List<Hotel>,
    private val onHotelItemClickListener: HotelItemClickListener
) : RecyclerView.Adapter<HotelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.hotel_item_ui,
                parent,
                false
            ), onHotelItemClickListener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(items[position])
    }
}