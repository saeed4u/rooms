package com.ut.iot.rooms.api.adapter

import androidx.lifecycle.LiveData
import com.ut.iot.rooms.api.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by Saeed on 2019-10-18.
 *
 * An adapter to convert EcoRewards API responses to LiveData<ApiResponse<T>>
 */
class LiveDataCallAdapter<T>(private val type: Type) : CallAdapter<T, LiveData<ApiResponse<T>>> {
    override fun adapt(call: Call<T>): LiveData<ApiResponse<T>> {
        return object : LiveData<ApiResponse<T>>() {
            //a flag to tell us when we can make the API call
            val startedCheck = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (startedCheck.compareAndSet(false, true)) {
                    //let's make the API call shall we?

                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            postValue(ApiResponse(t))
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(ApiResponse(response))
                        }

                    })

                }
            }
        }
    }

    override fun responseType(): Type {
        return type
    }

}