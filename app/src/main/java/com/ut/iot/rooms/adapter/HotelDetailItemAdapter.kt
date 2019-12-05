package com.ut.iot.rooms.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.viewholder.HotelDetailItemViewHolder
import com.ut.iot.rooms.data.model.HotelDetail
import com.ut.iot.rooms.data.model.HotelDetailType

/**
 * Created by Saeed on 02/12/2019.
 */
class HotelDetailItemAdapter(private val items: List<HotelDetail>) :
    RecyclerView.Adapter<HotelDetailItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelDetailItemViewHolder {
        return when(viewType){
            HotelDetailType.TITLE.ordinal -> HotelDetailItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.hotel_detail_item_ui,
                    parent,
                    false
                ))
            HotelDetailType.PRICE.ordinal -> HotelDetailItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.hotel_detail_price_item,
                    parent,
                    false
                ))
            HotelDetailType.RATING.ordinal -> HotelDetailItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.rating_item_ui,
                    parent,
                    false
                ))
            else -> HotelDetailItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.hotel_detail_item_ui_items,
                    parent,
                    false
                ))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].hotelDetailType.ordinal
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelDetailItemViewHolder, position: Int) {
        return holder.bind(items[position])
    }
}