package com.ut.iot.rooms.fcm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ut.iot.rooms.util.PUSH_NOTIFICATION
import com.ut.iot.rooms.api.model.device.UpdateFCMToken
import com.ut.iot.rooms.data.EventBus
import com.ut.iot.rooms.state.StateManager
import timber.log.Timber

/**
 * Created on 13/03/2019.
 */
class PushNotificationService : FirebaseMessagingService() {

    private val TAG = PushNotificationService::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
    }

    @SuppressLint("CheckResult")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val stateManager = StateManager(applicationContext)
        stateManager.saveFCMtoken(token)
        EventBus.publish(UpdateFCMToken(token))
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("From: %s", remoteMessage.from!!)

        if (remoteMessage.data.isNotEmpty()) {
            Timber.v(TAG, "Data Payload: %s", remoteMessage.data.toString())
            handleDataMessage(remoteMessage.data)
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        Timber.tag(TAG).v("push json: %s", data.toString())
        val title = data["title"]
        val message = data["desc"]

        Timber.v("title: $title")
        Timber.v("message: $message")

        // app is in foreground, broadcast the push message
        val pushNotification = Intent(PUSH_NOTIFICATION)

        pushNotification.putExtra("message", message)
        pushNotification.putExtra("title",title)
        pushNotification.putExtra("push_notification", true)
        pushNotification.putExtra("type", data["type"])
        pushNotification.putExtra("payload", data["payload"])
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
    }

}