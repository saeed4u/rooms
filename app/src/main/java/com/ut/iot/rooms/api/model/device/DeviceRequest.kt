package com.ut.iot.rooms.api.model.device

/**
 * Created on 10/02/2019.
 */
data class DeviceRequest(val imei: String, val app_version: String, val os: String, val os_version: String, val fcm_token:String = "")