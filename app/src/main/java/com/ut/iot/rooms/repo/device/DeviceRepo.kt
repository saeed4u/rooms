package com.ut.iot.rooms.repo.device

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.device.DeviceRequest
import com.ut.iot.rooms.api.model.device.DeviceResponse
import com.ut.iot.rooms.api.model.device.UpdateFCMToken
import com.ut.iot.rooms.api.service.DeviceService
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.repo.NetworkBoundRepo
import com.ut.iot.rooms.state.StateManager
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 2019-10-18.
 */
class DeviceRepo @Inject constructor(
    private val stateManager: StateManager,
    private val deviceService: DeviceService
) {

    init {
        Timber.d("Initializing device repo")
    }

    fun registerDevice(deviceRequest: DeviceRequest): LiveData<Resource<Int>> {
        return object : NetworkBoundRepo<Int, DeviceResponse>() {
            override fun loadFromLocalStorage(): LiveData<Int> {
                return MutableLiveData(stateManager.getDeviceId())
            }

            override fun saveToLocalStorage(data: DeviceResponse) {
                Timber.d("Device response $data")
                stateManager.saveDeviceId(data.device.id)
            }

            override fun shouldFetchData(data: Int?): Boolean {
                Timber.d("Data $data")
                return data == 0
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<DeviceResponse>> {
                Timber.d("Fetching data")
                return deviceService.registerDevice(deviceRequest)
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("Device registration failed reason $message")
            }

        }.getLiveData()
    }

    fun updateFcmToken(updateFCMToken: UpdateFCMToken): LiveData<Resource<Int>> {
        return object : NetworkBoundRepo<Int, DeviceResponse>() {
            override fun loadFromLocalStorage(): LiveData<Int> {
                return MutableLiveData(stateManager.getDeviceId())
            }

            override fun saveToLocalStorage(data: DeviceResponse) {
                //do nothing
            }

            override fun shouldFetchData(data: Int?): Boolean {
                return true
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<DeviceResponse>> {
                return deviceService.updateFCMToken(updateFCMToken)
            }

            override fun onFetchFailed(message: String?) {
            }

        }.getLiveData()
    }

    fun isDeviceRegistered() = stateManager.getDeviceId() > 0
}