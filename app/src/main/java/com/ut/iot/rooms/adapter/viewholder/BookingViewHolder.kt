package com.ut.iot.rooms.adapter.viewholder

import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.data.model.Booking
import kotlinx.android.synthetic.main.my_booking_item_ui.view.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Saeed on 28/11/2019.
 */
class BookingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(booking: Booking) {
        val isNightMode =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        Timber.d("IsNightmode $isNightMode")
        val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
        view.date.text = dateFormat.format(
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).parse(booking.start_date)!!
        )
        view.hotel_name.text = "Test Hotel"
        if (isNightMode) {
            view.booking_details.setBackgroundResource(R.drawable.my_booking_item_night_bg)
            view.hotel_name.setTextColor(ContextCompat.getColor(view.context, R.color.lightTextColor))
            view.date.setTextColor(ContextCompat.getColor(view.context, R.color.lightTextColor))

        } else {
            view.booking_details.setBackgroundResource(R.drawable.my_booking_item_bg)
            view.hotel_name.setTextColor(ContextCompat.getColor(view.context, R.color.black))
            view.date.setTextColor(ContextCompat.getColor(view.context, R.color.black))
        }

    }

}