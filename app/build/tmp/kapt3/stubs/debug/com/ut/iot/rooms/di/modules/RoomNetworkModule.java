package com.ut.iot.rooms.di.modules;

import java.lang.System;

/**
 * Created by Saeed on 2019-06-14.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0001\u00a2\u0006\u0002\b\u0007J\u0015\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0001\u00a2\u0006\u0002\b\u000b\u00a8\u0006\f"}, d2 = {"Lcom/ut/iot/rooms/di/modules/RoomNetworkModule;", "", "()V", "providesOkHttpClient", "Lokhttp3/OkHttpClient;", "stateManager", "Lcom/ut/iot/rooms/state/StateManager;", "providesOkHttpClient$app_debug", "providesRetrofitInterfaceForEcoRewardsAPI", "Lretrofit2/Retrofit;", "okHttpClient", "providesRetrofitInterfaceForEcoRewardsAPI$app_debug", "app_debug"})
@dagger.Module()
public final class RoomNetworkModule {
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.OkHttpClient providesOkHttpClient$app_debug(@org.jetbrains.annotations.NotNull()
    com.ut.iot.rooms.state.StateManager stateManager) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final retrofit2.Retrofit providesRetrofitInterfaceForEcoRewardsAPI$app_debug(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient) {
        return null;
    }
    
    public RoomNetworkModule() {
        super();
    }
}