package com.ut.iot.rooms.repo.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.BaseResponse
import com.ut.iot.rooms.api.model.auth.AuthRequest
import com.ut.iot.rooms.api.model.auth.AuthResponse
import com.ut.iot.rooms.api.service.AuthService
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.data.model.UserInfo
import com.ut.iot.rooms.db.dao.*
import com.ut.iot.rooms.repo.NetworkBoundRepo
import com.ut.iot.rooms.state.StateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 27/11/2019.
 */
class AuthRepo @Inject constructor(
    private val userDao: UserDao,
    private val bookingDao: BookingDao,
    private val roomDao: RoomDao,
    private val roomTypeDao: RoomTypeDao,
    private val imageDao: ImageDao,
    private val authService: AuthService,
    private val stateManager: StateManager
) {

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        Timber.d("Initializing auth repo")
    }

    fun auth(authRequest: AuthRequest, isLogin: Boolean = true): LiveData<Resource<UserInfo>> {
        return object : NetworkBoundRepo<UserInfo, AuthResponse>() {
            override fun loadFromLocalStorage(): LiveData<UserInfo> {
                return userDao.getLoggedInUser()
            }

            override fun saveToLocalStorage(data: AuthResponse) {
                stateManager.saveAuthToken(data.user.token)
                stateManager.saveUserId(data.user.id)
                stateManager.setLoggedInState(true)
                scope.launch {
                    val user = data.user
                    userDao.insertAll(user)

                    val bookings = user.bookings.onEach {
                        it.user_id = user.id
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

            override fun shouldFetchData(data: UserInfo?): Boolean {
                return true
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<AuthResponse>> {
                if (isLogin){
                    return authService.login(authRequest)
                }
                return authService.register(authRequest)
            }

            override fun onFetchFailed(message: String?) {
            }

        }.getLiveData()
    }


    fun logout(): LiveData<Resource<Boolean>> {
        return object : NetworkBoundRepo<Boolean, BaseResponse>() {
            override fun loadFromLocalStorage(): LiveData<Boolean> {
                return MutableLiveData(false)
            }

            override fun saveToLocalStorage(data: BaseResponse) {
                stateManager.saveAuthToken("")
                stateManager.setLoggedInState(false)
                scope.launch {
                    userDao.deleteAll()
                    bookingDao.deleteAll()
                    roomDao.deleteAll()
                    roomTypeDao.deleteAll()
                    imageDao.deleteAll()
                }
            }

            override fun shouldFetchData(data: Boolean?): Boolean {
                //here we are going to return true always
                return true
            }

            override fun fetchDataFromApi(): LiveData<ApiResponse<BaseResponse>> {
                return authService.logout()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("log out failed: reason $message")
            }

        }.getLiveData()
    }

}