package com.ut.iot.rooms.ui.home.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ut.iot.rooms.data.model.Hotel
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.repo.hotel.HotelRepo
import com.ut.iot.rooms.util.NullLiveData
import javax.inject.Inject

class HotelViewModel @Inject constructor(private val hotelRepo: HotelRepo) : ViewModel() {

    private val hotelRequest = MutableLiveData<Boolean>()
    val hotelResponse: LiveData<Resource<List<Hotel>>>

    init {
        hotelResponse = hotelRequest.switchMap {
            hotelRequest.value?.let {
                hotelRepo.getHotels(it)
            } ?: NullLiveData.create()
        }
    }

    public fun getHotels(refresh: Boolean = false) = hotelRequest.postValue(refresh)

}