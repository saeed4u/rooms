package com.ut.iot.rooms.di.modules

import com.ut.iot.rooms.di.modules.sub.AuthFragmentModule
import com.ut.iot.rooms.di.modules.sub.HomeFragmentModule
import com.ut.iot.rooms.ui.auth.AuthActivity
import com.ut.iot.rooms.ui.home.HomeActivity
import com.ut.iot.rooms.ui.home.booking.BookingActivity
import com.ut.iot.rooms.ui.home.hotel.detail.HotelDetailActivity
import com.ut.iot.rooms.ui.settings.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Saeed on 27/11/2019.
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [AuthFragmentModule::class])
    abstract fun providesAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun providesHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun providesHotelDetailActivity(): HotelDetailActivity

    @ContributesAndroidInjector
    abstract fun providesBookingActivity(): BookingActivity

    @ContributesAndroidInjector
    abstract fun providesSettingsActivity(): SettingsActivity

}