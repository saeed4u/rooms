package com.ut.iot.rooms.repo.hotel

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.hotel.HotelResponse
import com.ut.iot.rooms.api.model.hotel.HotelsResponse
import com.ut.iot.rooms.api.service.HotelService
import com.ut.iot.rooms.data.model.Hotel
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.db.dao.HotelDao
import com.ut.iot.rooms.repo.NetworkBoundRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 30/11/2019.
 */
class HotelRepo @Inject constructor(private val hotelDao: HotelDao, private val hotelService: HotelService) {

    val scope = CoroutineScope(Dispatchers.IO)

    fun getHotels(refresh: Boolean): LiveData<Resource<List<Hotel>>>{
        return object : NetworkBoundRepo<List<Hotel>, HotelsResponse>(){
            override fun loadFromLocalStorage(): LiveData<List<Hotel>> {
                return hotelDao.getHotels()
            }

            override fun saveToLocalStorage(data: HotelsResponse) {
                scope.launch {
                    with(data.hotels){
                        onEach {
                            it.price = (100..200).random().toDouble()
                        }
                        hotelDao.insertAll(*toTypedArray())
                    }
                }
            }

            override fun shouldFetchData(data: List<Hotel>?): Boolean {
                return data == null || data.isEmpty() || refresh
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<HotelsResponse>> {
                return hotelService.getHotels()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("Failed $message")
            }

        }.getLiveData()
    }

    fun getHotel(hotelId: Int): LiveData<Resource<Hotel>>{
        return object  : NetworkBoundRepo<Hotel, HotelResponse>(){
            override fun loadFromLocalStorage(): LiveData<Hotel> {
                return hotelDao.getHotel(hotelId)
            }

            override fun saveToLocalStorage(data: HotelResponse) {
                scope.launch(Dispatchers.IO){
                    with(data.hotel){
                        price = (100..200).random().toDouble()
                        hotelDao.insertAll(this)
                    }
                }
            }

            override fun shouldFetchData(data: Hotel?): Boolean {
                return data == null
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<HotelResponse>> {
                return hotelService.getHotel(hotelId)
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("Message $message")
            }

        }.getLiveData()
    }

}