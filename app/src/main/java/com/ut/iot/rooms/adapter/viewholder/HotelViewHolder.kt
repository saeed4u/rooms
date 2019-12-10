package com.ut.iot.rooms.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ut.iot.rooms.R
import com.ut.iot.rooms.data.model.Hotel
import com.ut.iot.rooms.ui.home.hotel.HotelItemClickListener
import kotlinx.android.synthetic.main.horizontal_loader.view.*
import kotlinx.android.synthetic.main.hotel_item_ui.view.*
import java.text.DecimalFormat

/**
 * Created by Saeed on 30/11/2019.
 */
class HotelViewHolder(
    private val view: View,
    private val onHotelItemClickListener: HotelItemClickListener
) : RecyclerView.ViewHolder(view) {

    private val decimalFormat = DecimalFormat("0.00")

    public fun bind(hotel: Hotel) {
        view.hotel_name.text = hotel.name
        val rating = (3..5).random().toFloat()
        view.hotel_rating.rating = rating
        val image = hotel.images.first()
        view.loader.visibility = View.VISIBLE
        Picasso.get().load(image.src)
            .error(R.drawable.hotel1)
            .into(view.hotel_image, object : Callback {
                override fun onSuccess() {
                    view.hotel_image.visibility = View.VISIBLE
                    view.loader.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    view.hotel_image.visibility = View.VISIBLE
                    view.loader.visibility = View.GONE
                }

            })

        view.view_deal.setOnClickListener {
            onHotelItemClickListener.onItemClicked(hotel)
        }
    }

}