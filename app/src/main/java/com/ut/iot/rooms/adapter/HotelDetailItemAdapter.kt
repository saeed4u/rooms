package com.ut.iot.rooms.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.viewholder.HotelDetailItemViewHolder
import com.ut.iot.rooms.data.model.HotelDetail

/**
 * Created by Saeed on 02/12/2019.
 */
class HotelDetailItemAdapter(private val items: List<HotelDetail>) :
    RecyclerView.Adapter<HotelDetailItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelDetailItemViewHolder {
        return HotelDetailItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.hotel_detail_item_ui,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelDetailItemViewHolder, position: Int) {
        return holder.bind(items[position])
    }
}