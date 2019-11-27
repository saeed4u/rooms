package com.ut.iot.rooms.state;

import java.lang.System;

/**
 * Created by Saeed on 2019-10-18.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0006J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0006J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0012J\u0010\u0010\u001b\u001a\u00020\u000f2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0012J\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/ut/iot/rooms/state/StateManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "AUTH_TOKEN", "", "DEVICE_ID", "FCM_TOKEN", "FIRST_TIME", "USER_ID", "USER_LOOGED_IN", "sharedPreferences", "Landroid/content/SharedPreferences;", "changeFirstTimeState", "", "getAuthToken", "getDeviceId", "", "getUserId", "isFirstTime", "", "isUserLoggedIn", "saveAuthToken", "authToken", "saveDeviceId", "deviceId", "saveFCMtoken", "fcmToken", "saveUserId", "userId", "setLoggedInState", "loggedInState", "app_debug"})
public final class StateManager {
    private android.content.SharedPreferences sharedPreferences;
    private final java.lang.String USER_LOOGED_IN = "user_logged_in";
    private final java.lang.String FIRST_TIME = "first_time";
    private final java.lang.String AUTH_TOKEN = "auth_token";
    private final java.lang.String DEVICE_ID = "device_id";
    private final java.lang.String FCM_TOKEN = "fcm_token";
    private final java.lang.String USER_ID = "user_id";
    
    public final void changeFirstTimeState() {
    }
    
    public final boolean isFirstTime() {
        return false;
    }
    
    public final void setLoggedInState(boolean loggedInState) {
    }
    
    public final boolean isUserLoggedIn() {
        return false;
    }
    
    public final void saveAuthToken(@org.jetbrains.annotations.NotNull()
    java.lang.String authToken) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAuthToken() {
        return null;
    }
    
    public final void saveDeviceId(int deviceId) {
    }
    
    public final int getDeviceId() {
        return 0;
    }
    
    public final void saveFCMtoken(@org.jetbrains.annotations.Nullable()
    java.lang.String fcmToken) {
    }
    
    public final void saveUserId(int userId) {
    }
    
    public final int getUserId() {
        return 0;
    }
    
    public StateManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
}