package com.ut.iot.rooms.di.modules

import com.ut.iot.rooms.api.RequestInterceptorForHeaders
import com.ut.iot.rooms.api.adapter.LiveDataCallAdapterFactory
import com.ut.iot.rooms.api.service.AuthService
import com.ut.iot.rooms.api.service.BookingService
import com.ut.iot.rooms.api.service.DeviceService
import com.ut.iot.rooms.state.StateManager
import com.ut.iot.rooms.util.API_BASE_URL
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
class RoomsNetworkModule {


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

    @Provides
    @Singleton
    internal fun providesDeviceService(retrofit: Retrofit): DeviceService {
        return retrofit.create(DeviceService::class.java)
    }

    @Provides
    @Singleton
    internal fun providesAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }


    @Provides
    @Singleton
    internal fun providesBookingService(retrofit: Retrofit): BookingService {
        return retrofit.create(BookingService::class.java)
    }

}