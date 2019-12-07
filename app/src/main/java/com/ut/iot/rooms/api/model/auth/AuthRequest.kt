package com.ut.iot.rooms.api.model.auth


/**
 * Created by Saeed on 27/11/2019.
 */
data class AuthRequest(val name: String? = null, val email: String, val password: String, @Transient val isLogin: Boolean = true)