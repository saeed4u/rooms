package com.ut.iot.rooms.repo.auth

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.auth.AuthRequest
import com.ut.iot.rooms.api.model.auth.AuthResponse
import com.ut.iot.rooms.api.service.DeviceService
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.data.model.User
import com.ut.iot.rooms.repo.NetworkBoundRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber


/**
 * Created by Saeed on 27/11/2019.
 */
class AuthRepo(deviceService: DeviceService) {

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        Timber.d("Initializing auth repo")
    }

    fun auth(authRequest: AuthRequest): LiveData<Resource<User>> {
        return object : NetworkBoundRepo<User, AuthResponse>(){
            override fun loadFromLocalStorage(): LiveData<User> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun saveToLocalStorage(data: AuthResponse) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetchData(data: User?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<AuthResponse>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFetchFailed(message: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }.getLiveData()
    }

}