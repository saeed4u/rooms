package com.ut.iot.rooms.di.components

import com.ut.iot.rooms.Rooms
import com.ut.iot.rooms.di.modules.ActivityBindingModule
import com.ut.iot.rooms.di.modules.PersistenceModule
import com.ut.iot.rooms.di.modules.RoomsNetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by Saeed on 27/11/2019.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, RoomsNetworkModule::class, ActivityBindingModule::class, PersistenceModule::class])
interface AppComponent : AndroidInjector<Rooms> {

    override fun inject(instance: Rooms)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(rooms: Rooms): Builder

        fun build(): AppComponent
    }

}