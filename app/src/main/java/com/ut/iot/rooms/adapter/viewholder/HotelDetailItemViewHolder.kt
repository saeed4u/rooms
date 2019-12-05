package com.ut.iot.rooms.adapter.viewholder


import android.annotation.SuppressLint
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.data.model.HotelDetail
import com.ut.iot.rooms.data.model.HotelDetailType
import java.text.DecimalFormat

/**
 * Created by Saeed on 02/12/2019.
 */
class HotelDetailItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

    @SuppressLint("SetTextI18n")
    fun bind(item: HotelDetail){
        when(item.hotelDetailType){
            HotelDetailType.RATING -> {
                view.findViewById<RatingBar>(R.id.hotel_rating).rating = item.value.toFloat()
            }
            HotelDetailType.TITLE, HotelDetailType.ROOM -> view.findViewById<TextView>(R.id.item).text = item.value
            HotelDetailType.PRICE ->{
                    view.findViewById<TextView>(R.id.pricing).text = "USD ${DecimalFormat("0.00").format(item.value.toDouble())} "
            }
        }
    }

}