package com.ut.iot.rooms.ui.home.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ut.iot.rooms.data.model.AddBookingRequest
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.repo.booking.BookingRepo
import com.ut.iot.rooms.util.NullLiveData
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * Created by Saeed on 08/12/2019.
 */
class BookingViewModel @Inject constructor(private val bookingRepo: BookingRepo) : ViewModel() {

    private val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())

    private val bookingFormStateLiveData = MutableLiveData<BookingFormState>()
    val bookingFormStateLiveData_ = bookingFormStateLiveData

    private val addBookingRequest = MutableLiveData<AddBookingRequest>()
    val addBookingResponse: LiveData<Resource<Boolean>>

    init {
        addBookingResponse = addBookingRequest.switchMap {
            addBookingRequest.value?.let {
                bookingRepo.addBooking(it)
            } ?: NullLiveData.create()
        }
    }


    fun formStateChanged(startDate: String, endDate: String, roomId: Int? = 0) {
        val bookingFormState = BookingFormState()

        val utcDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())

        if (startDate.isNotEmpty()) {
            bookingFormState.startDateFormatted =
                dateFormat.format(
                    utcDateFormat.parse(
                        startDate
                    )!!
                )
            bookingFormState.startDate = startDate
        } else {
            bookingFormState.isValid = false
        }

        if (endDate.isNotEmpty()) {
            bookingFormState.endDateFormatted =
                dateFormat.format(
                   utcDateFormat.parse(endDate)!!
                )
            bookingFormState.endDate = endDate
        } else {
            bookingFormState.isValid = false
        }
        if (startDate.isNotEmpty() && endDate.isNotEmpty()){
            val startDateMillis = utcDateFormat.parse(startDate)!!.time
            val endDateLongMillis = utcDateFormat.parse(endDate)!!.time

            val diff = endDateLongMillis - startDateMillis
            val days = diff.div(24.times(60.times(60).times(1000)))

            if (days < 1){
                bookingFormState.isValid = false
            }
            bookingFormState.numberOfDays = days
        }
        bookingFormState.roomId = roomId
        if (roomId == null) {
            bookingFormState.isValid = false
        }
        bookingFormStateLiveData.value = bookingFormState
    }

    fun addBooking(addBooking: AddBookingRequest) = addBookingRequest.postValue(addBooking)
}