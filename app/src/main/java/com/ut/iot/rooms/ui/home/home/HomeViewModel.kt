package com.ut.iot.rooms.ui.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ut.iot.rooms.data.model.Booking
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.repo.booking.BookingRepo
import com.ut.iot.rooms.util.NullLiveData
import javax.inject.Inject

class HomeViewModel @Inject constructor(val bookingRepo: BookingRepo) : ViewModel() {

    private val bookingRequest: MutableLiveData<Boolean> = MutableLiveData()
    val bookingResponse: LiveData<Resource<List<Booking>>>

    init {
        bookingResponse = bookingRequest.switchMap {
            bookingRequest.value?.let {
                bookingRepo.getBookings(it)
            } ?: NullLiveData.create()
        }
    }

    public fun getBookings(refresh: Boolean = false) = bookingRequest.postValue(refresh)

}