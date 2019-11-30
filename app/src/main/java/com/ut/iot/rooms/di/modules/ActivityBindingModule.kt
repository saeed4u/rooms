package com.ut.iot.rooms.di.modules

import com.ut.iot.rooms.di.modules.sub.AuthFragmentModule
import com.ut.iot.rooms.di.modules.sub.HomeFragmentModule
import com.ut.iot.rooms.ui.auth.AuthActivity
import com.ut.iot.rooms.ui.home.HomeActivity
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

}