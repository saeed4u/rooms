package com.ut.iot.rooms.adapter.viewholder


import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ut.iot.rooms.R
import com.ut.iot.rooms.data.model.HotelDetail
import com.ut.iot.rooms.data.model.HotelDetailType
import com.ut.iot.rooms.data.model.Room
import timber.log.Timber
import java.text.DecimalFormat

/**
 * Created by Saeed on 02/12/2019.
 */
class HotelDetailItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(item: HotelDetail) {
        if (item.hotelDetailType == HotelDetailType.LOCATION){
            view.findViewById<TextView>(R.id.item).text = item.value
        }
        when (item.hotelDetailType) {
            HotelDetailType.RATING -> {
                view.findViewById<RatingBar>(R.id.hotel_rating).rating = item.value.toFloat()
            }
            HotelDetailType.TITLE -> {
                view.findViewById<TextView>(R.id.item).text = item.value
            }
            HotelDetailType.ROOM -> {
                view.findViewById<TextView>(R.id.item).text = item.value
                view.findViewById<TextView>(R.id.pricing).text =
                    "USD ${DecimalFormat("0.00").format(item.otherValue.toDouble())} "
                val room = item.item as Room
                val firstImage = room.images?.first()
                firstImage?.apply {
                    Picasso.get().load(src)
                        .into(
                            view.findViewById<ImageView>(R.id.room_image)
                        )
                }
            }
            HotelDetailType.LOCATION -> {
                Timber.d("Value ${item.value}")
                view.findViewById<TextView>(R.id.item).text = item.value
            }

        }
    }

}