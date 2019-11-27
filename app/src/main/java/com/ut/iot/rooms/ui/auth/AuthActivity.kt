package com.ut.iot.rooms.ui.auth

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Observer
import com.ut.iot.rooms.BuildConfig
import com.ut.iot.rooms.R
import com.ut.iot.rooms.api.model.device.Device
import com.ut.iot.rooms.api.model.device.DeviceRequest
import com.ut.iot.rooms.api.model.device.DeviceResponse
import com.ut.iot.rooms.data.EventBus
import com.ut.iot.rooms.data.model.ResourceLoading
import com.ut.iot.rooms.ui.BaseActivity
import timber.log.Timber


/**
 * Created by Saeed on 27/11/2019.
 */
class AuthActivity : BaseActivity() {
    override fun handleResourceLoading(resourceLoading: ResourceLoading?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        if (!deviceRepo.isDeviceRegistered()) {
            registerDevice()
        }
    }


    @SuppressLint("HardwareIds")
    private fun registerDevice() {
        Timber.d("Registering device")
        val deviceId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        deviceRepo.registerDevice(
            DeviceRequest(
                deviceId,
                BuildConfig.VERSION_NAME,
                "android",
                Build.VERSION.CODENAME
            )
        ).observe(this, Observer {
            it.data?.apply {
                EventBus.publish(DeviceResponse(Device(this)))
            }
        })
    }

}