package com.ut.iot.rooms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.viewholder.BookingViewHolder
import com.ut.iot.rooms.data.model.Booking

/**
 * Created by Saeed on 28/11/2019.
 */
class BookingAdapter(private val bookings: List<Booking>) :
    RecyclerView.Adapter<BookingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        return BookingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.my_booking_item_ui,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(bookings[position])
    }


}