package com.ut.iot.rooms.ui.home.hotel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ut.iot.rooms.data.model.Hotel
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.repo.hotel.HotelRepo
import com.ut.iot.rooms.util.NullLiveData
import javax.inject.Inject


/**
 * Created by Saeed on 02/12/2019.
 */
class HotelDetailViewModel @Inject constructor(private val hotelRepo: HotelRepo) : ViewModel() {


    private val hotelRequest = MutableLiveData<Int>()
    val hotelResponse: LiveData<Resource<Hotel>>

    init {
        hotelResponse = hotelRequest.switchMap {
            hotelRequest.value?.let {
                hotelRepo.getHotel(it)
            } ?: NullLiveData.create()
        }
    }

    public fun getHotel(hotelId: Int) = hotelRequest.postValue(hotelId)


}