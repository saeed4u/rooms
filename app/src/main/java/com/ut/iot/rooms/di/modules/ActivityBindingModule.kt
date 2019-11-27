package com.ut.iot.rooms.di.modules

import com.ut.iot.rooms.ui.auth.AuthActivity
import com.ut.iot.rooms.ui.auth.AuthBaseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Saeed on 27/11/2019.
 */
@Module
interface ActivityBindingModule {

    @ContributesAndroidInjector(modules = [AuthBaseFragment::class])
    fun providesAuthActivity(): AuthActivity


}