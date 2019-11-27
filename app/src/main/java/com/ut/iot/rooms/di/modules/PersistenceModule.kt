package com.ut.iot.rooms.di.modules

import com.ut.iot.rooms.Rooms
import com.ut.iot.rooms.state.StateManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Saeed on 2019-10-18.
 */
@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providesStateManager(rooms: Rooms): StateManager {
        return StateManager(rooms)
    }


}