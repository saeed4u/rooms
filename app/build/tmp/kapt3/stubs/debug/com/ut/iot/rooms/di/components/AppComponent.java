package com.ut.iot.rooms.di.components;

import java.lang.System;

/**
 * Created by Saeed on 27/11/2019.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0006J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0002H&\u00a8\u0006\u0007"}, d2 = {"Lcom/ut/iot/rooms/di/components/AppComponent;", "Ldagger/android/AndroidInjector;", "Lcom/ut/iot/rooms/Rooms;", "inject", "", "instance", "Builder", "app_debug"})
@dagger.Component(modules = {dagger.android.support.AndroidSupportInjectionModule.class, com.ut.iot.rooms.di.modules.RoomNetworkModule.class})
@javax.inject.Singleton()
public abstract interface AppComponent extends dagger.android.AndroidInjector<com.ut.iot.rooms.Rooms> {
    
    @java.lang.Override()
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    com.ut.iot.rooms.Rooms instance);
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H\'J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/ut/iot/rooms/di/components/AppComponent$Builder;", "", "application", "rooms", "Lcom/ut/iot/rooms/Rooms;", "build", "Lcom/ut/iot/rooms/di/components/AppComponent;", "app_debug"})
    @dagger.Component.Builder()
    public static abstract interface Builder {
        
        @org.jetbrains.annotations.NotNull()
        @dagger.BindsInstance()
        public abstract com.ut.iot.rooms.di.components.AppComponent.Builder application(@org.jetbrains.annotations.NotNull()
        com.ut.iot.rooms.Rooms rooms);
        
        @org.jetbrains.annotations.NotNull()
        public abstract com.ut.iot.rooms.di.components.AppComponent build();
    }
}