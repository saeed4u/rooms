package com.ut.iot.rooms.di.modules

import com.ut.iot.rooms.API_BASE_URL
import com.ut.iot.rooms.api.RequestInterceptorForHeaders
import com.ut.iot.rooms.api.adapter.LiveDataCallAdapterFactory
import com.ut.iot.rooms.state.StateManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Saeed on 2019-06-14.
 */
@Module
class RoomNetworkModule {


    @Provides
    @Singleton
    internal fun providesOkHttpClient(stateManager: StateManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptorForHeaders(stateManager)).addInterceptor(loggingInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES).retryOnConnectionFailure(true).build()
    }


    @Provides
    @Singleton
    internal fun providesRetrofitInterfaceForEcoRewardsAPI(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

}