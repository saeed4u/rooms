package com.ut.iot.rooms.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/**
 * Created on 27/04/2019.
 */
class NetworkConnectivityReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityIntent = Intent(NETWORK_ACTION)
        connectivityIntent.putExtra(NETWORK_AVAILABILITY, hasInternet(context))
        LocalBroadcastManager.getInstance(context).sendBroadcast(connectivityIntent)
    }

    private fun hasInternet(context: Context): Boolean {
        try {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = manager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return false
    }

}