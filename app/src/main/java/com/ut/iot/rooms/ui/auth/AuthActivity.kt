package com.ut.iot.rooms.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
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
import com.ut.iot.rooms.state.StateManager
import com.ut.iot.rooms.ui.BaseActivity
import com.ut.iot.rooms.ui.home.HomeActivity
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 27/11/2019.
 */
class AuthActivity : BaseActivity() {

    @Inject
    lateinit var stateManager: StateManager
    override fun handleResourceLoading(resourceLoading: ResourceLoading?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (stateManager.isUserLoggedIn()) {
            val dashboardIntent = Intent(this, HomeActivity::class.java)
            dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.extras?.keySet()?.forEach {
                Timber.d("Key $it, value ${intent.extras?.get(it)}")
                dashboardIntent.putExtra(it, intent.extras!!.getString(it))
            }
            startActivity(dashboardIntent)
        }else {
            setContentView(R.layout.auth_activity)
            if (!deviceRepo.isDeviceRegistered()) {
                registerDevice()
            }
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