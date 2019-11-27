package com.ut.iot.rooms.di.modules

import com.ut.iot.rooms.di.modules.sub.AuthFragmentModule
import com.ut.iot.rooms.ui.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Saeed on 27/11/2019.
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [AuthFragmentModule::class])
    abstract fun providesAuthActivity(): AuthActivity


}