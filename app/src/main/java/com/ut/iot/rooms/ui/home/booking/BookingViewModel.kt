package com.ut.iot.rooms.ui.home.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Saeed on 08/12/2019.
 */
class BookingViewModel : ViewModel() {

    private val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())

    private val bookingFormStateLiveData = MutableLiveData<BookingFormState>()
    val bookingFormStateLiveData_ = bookingFormStateLiveData


    fun formStateChanged(startDate: String, endDate: String, roomId: Int = 0) {
        val bookingFormState = BookingFormState()

        if (startDate.isNotEmpty()) {
            bookingFormState.startDateFormatted =
                dateFormat.format(
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
                        startDate
                    )!!
                )
            bookingFormState.startDate = startDate
        } else {
            bookingFormState.isValid = false
        }

        if (endDate.isNotEmpty()) {
            bookingFormState.startDateFormatted =
                dateFormat.format(
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).parse(endDate)!!
                )
            bookingFormState.endDate = endDate
        } else {
            bookingFormState.isValid = false
        }
        bookingFormState.roomId = roomId
        if (roomId == 0) {
            bookingFormState.isValid = false
        }
        bookingFormStateLiveData.value = bookingFormState
    }
}