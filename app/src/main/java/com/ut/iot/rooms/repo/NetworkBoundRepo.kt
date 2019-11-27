package com.ut.iot.rooms.repo

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.squareup.moshi.Moshi
import com.ut.iot.rooms.api.ApiResponse
import com.ut.iot.rooms.api.model.BaseResponse
import com.ut.iot.rooms.data.BannerMessage
import com.ut.iot.rooms.data.BannerType
import com.ut.iot.rooms.data.EventBus
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.data.model.ResourceLoading
import timber.log.Timber


/**
 * Created by Saeed on 2019-10-18.
 */
abstract class NetworkBoundRepo<R, RQ> {

    private val data: MediatorLiveData<Resource<R>> = MediatorLiveData()

    init {
        try {
            EventBus.publish(ResourceLoading.LOADING)
            val dataFromDB = this.loadFromLocalStorage()
            data.addSource(dataFromDB) { result ->
                data.removeSource(dataFromDB)
                if (shouldFetchData(result)) {
                    data.postValue(Resource.loading(null))
                    fetchDataFromNetwork(dataFromDB)
                } else {
                    data.addSource<R>(dataFromDB) {
                        setData(Resource.success(it))
                    }
                }


            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun fetchDataFromNetwork(dataFromDB: LiveData<R>) {
        val networkResponse = fetchDataFromApi()
        data.addSource(networkResponse) { response ->
            Timber.d("Response response")
            response?.let { it ->
                when (it.isSuccessful) {
                    true -> {
                        response.body?.let { requestType ->
                            saveToLocalStorage(requestType)
                            val loadedData = loadFromLocalStorage()
                            data.addSource(loadedData) { newData ->
                                setData(Resource.success(newData))
                            }
                        }
                    }
                    false -> {
                        data.removeSource(dataFromDB)
                        response.message?.let { errorBody ->
                            onFetchFailed(errorBody)
                            try {
                                val baseResponse =
                                    Moshi.Builder().build().adapter(BaseResponse::class.java)
                                        .fromJson(errorBody)


                                baseResponse?.let {
                                    EventBus.publish(
                                        BannerMessage(
                                            BannerType.ERROR,
                                            it.message,
                                            it.status_code
                                        )
                                    )
                                }
                                data.addSource<R>(dataFromDB) { newData ->
                                    setData(Resource.error(errorBody, newData))
                                }
                            } catch (ex: java.lang.Exception) {
                                EventBus.publish(ResourceLoading.DONE)
                                EventBus.publish(
                                    BannerMessage(
                                        BannerType.ERROR,
                                        "An error occurred while processing your request, please try again later."
                                    )
                                )
                            }
                        }
                    }
                }
            }

        }
    }


    @MainThread
    private fun setData(newData: Resource<R>) {
        data.value = newData
        EventBus.publish(ResourceLoading.DONE)
    }

    fun getLiveData(): LiveData<Resource<R>> {
        return data
    }

    @WorkerThread
    protected abstract fun loadFromLocalStorage(): LiveData<R>

    @WorkerThread
    protected abstract fun saveToLocalStorage(data: RQ)

    @MainThread
    protected abstract fun shouldFetchData(data: R?): Boolean

    @MainThread
    protected abstract fun fetchDataFromApi(): LiveData<ApiResponse<RQ>>

    @MainThread
    protected abstract fun onFetchFailed(message: String?)


}