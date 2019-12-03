package com.ut.iot.rooms.adapter.viewholder


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.data.model.HotelDetail
import kotlinx.android.synthetic.main.hotel_detail_item_ui.view.*

/**
 * Created by Saeed on 02/12/2019.
 */
class HotelDetailItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

    fun bind(item: HotelDetail){
        view.item_title.text = item.title
        view.item_value.text = item.value
    }

}