package com.ut.iot.rooms.repo.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.booking.BookingResponse
import com.ut.iot.rooms.api.model.booking.BookingsResponse
import com.ut.iot.rooms.api.service.BookingService
import com.ut.iot.rooms.data.model.AddBookingRequest
import com.ut.iot.rooms.data.model.Booking
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.db.dao.BookingDao
import com.ut.iot.rooms.db.dao.ImageDao
import com.ut.iot.rooms.db.dao.RoomDao
import com.ut.iot.rooms.db.dao.RoomTypeDao
import com.ut.iot.rooms.repo.NetworkBoundRepo
import com.ut.iot.rooms.state.StateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 28/11/2019.
 */
class BookingRepo @Inject constructor(
    private val bookingDao: BookingDao,
    private val roomDao: RoomDao,
    private val roomTypeDao: RoomTypeDao,
    private val imageDao: ImageDao,
    private val stateManager: StateManager,
    private val bookingService: BookingService
) {

    val scope = CoroutineScope(Dispatchers.IO)

    fun getBookings(refresh: Boolean): LiveData<Resource<List<Booking>>> {
        return object : NetworkBoundRepo<List<Booking>, BookingsResponse>() {
            override fun loadFromLocalStorage(): LiveData<List<Booking>> {
                return bookingDao.getUserBookings(stateManager.getUserId())
            }

            override fun saveToLocalStorage(data: BookingsResponse) {
                scope.launch {
                    val bookings = data.bookings.onEach {
                        it.user_id = stateManager.getUserId()
                        it.room?.apply {
                            it.room_id = id
                        }
                    }
                    val rooms = bookings.map {
                        it.room?.type_id = it.room?.type?.id
                        it.room!!
                    }
                    val roomTypes = rooms.map { it.type!! }

                    roomTypeDao.insertAll(*roomTypes.toTypedArray())
                    roomDao.insertAll(*rooms.toTypedArray())
                    rooms.forEach { room ->
                        room.images!!.onEach {
                            it.room_id = room.id
                        }
                        imageDao.insertAll(*room.images!!.toTypedArray())
                    }
                    bookingDao.insertAll(*bookings.toTypedArray())
                }
            }

            override fun shouldFetchData(data: List<Booking>?): Boolean {
                return data == null || data.isEmpty() || refresh
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<BookingsResponse>> {
                return bookingService.getUserBookings()
            }

            override fun onFetchFailed(message: String?) {
            }

        }.getLiveData()
    }

    fun addBooking(addBookingRequest: AddBookingRequest): LiveData<Resource<Boolean>> {
        return object : NetworkBoundRepo<Boolean, BookingResponse>() {
            override fun loadFromLocalStorage(): LiveData<Boolean> {
                return MutableLiveData(true)
            }

            override fun saveToLocalStorage(data: BookingResponse) {
                scope.launch {
                    data.booking.apply {
                        user_id = stateManager.getUserId()
                        room?.apply {
                            type?.apply {
                                type_id = id
                                roomTypeDao.insertAll(this)
                            }
                            roomDao.insertAll(this)
                        }
                        bookingDao.insertAll(this)
                    }
                }
            }

            override fun shouldFetchData(data: Boolean?): Boolean {
                return true
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<BookingResponse>> {
                return bookingService.addBooking(addBookingRequest)
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("Error $message")
            }

        }.getLiveData()
    }

}