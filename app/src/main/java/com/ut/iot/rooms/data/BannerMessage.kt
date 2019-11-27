package com.ut.iot.rooms.data


/**
 * Created by Saeed on 22/10/2019.
 */
enum class BannerType {
    ERROR, SUCCESS
}

data class BannerMessage(val bannerType: BannerType, val message: String, var statusCode: String = "")