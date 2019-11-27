package com.ut.iot.rooms.state

import android.content.Context
import android.content.SharedPreferences
import com.ut.iot.rooms.R


/**
 * Created by Saeed on 2019-10-18.
 */
class StateManager(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private val USER_LOOGED_IN = "user_logged_in"
    private val FIRST_TIME = "first_time"
    private val AUTH_TOKEN = "auth_token"
    private val DEVICE_ID = "device_id"
    private val FCM_TOKEN = "fcm_token"
    private val USER_ID = "user_id"

    fun changeFirstTimeState() =
        sharedPreferences.edit().putBoolean(FIRST_TIME, false).apply()

    fun isFirstTime() = sharedPreferences.getBoolean(FIRST_TIME, true)

    fun setLoggedInState(loggedInState: Boolean) =
        sharedPreferences.edit().putBoolean(USER_LOOGED_IN, loggedInState).apply()

    fun isUserLoggedIn() = sharedPreferences.getBoolean(USER_LOOGED_IN, false)

    fun saveAuthToken(authToken: String) =
        sharedPreferences.edit().putString(AUTH_TOKEN, authToken).apply()

    fun getAuthToken(): String = sharedPreferences.getString(AUTH_TOKEN, "")!!

    fun saveDeviceId(deviceId: Int) =
        sharedPreferences.edit().putInt(DEVICE_ID, deviceId).apply()

    fun getDeviceId(): Int = sharedPreferences.getInt(DEVICE_ID, 0)

    fun saveFCMtoken(fcmToken: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(FCM_TOKEN, fcmToken)
        editor.apply()
    }

    fun saveUserId(userId: Int) =
        sharedPreferences.edit().putInt(USER_ID, userId).apply()

    fun getUserId(): Int = sharedPreferences.getInt(USER_ID, 0)


}