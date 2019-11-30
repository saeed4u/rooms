package com.ut.iot.rooms.di.modules.sub

import com.ut.iot.rooms.ui.home.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Saeed on 28/11/2019.
 */
@Module
abstract class HomeFragmentModule {

    @ContributesAndroidInjector
    abstract fun providesHomeFragment(): HomeFragment

}