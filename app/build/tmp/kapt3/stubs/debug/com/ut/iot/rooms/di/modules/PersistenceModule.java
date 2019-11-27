package com.ut.iot.rooms.di.modules;

import java.lang.System;

/**
 * Created by Saeed on 2019-10-18.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/ut/iot/rooms/di/modules/PersistenceModule;", "", "()V", "providesStateManager", "Lcom/ut/iot/rooms/state/StateManager;", "rooms", "Lcom/ut/iot/rooms/Rooms;", "app_debug"})
@dagger.Module()
public final class PersistenceModule {
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.ut.iot.rooms.state.StateManager providesStateManager(@org.jetbrains.annotations.NotNull()
    com.ut.iot.rooms.Rooms rooms) {
        return null;
    }
    
    public PersistenceModule() {
        super();
    }
}