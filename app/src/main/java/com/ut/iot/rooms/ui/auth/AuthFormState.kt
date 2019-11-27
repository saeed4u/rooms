package com.ut.iot.rooms.ui.auth


/**
 * Created by Saeed on 2019-10-17.
 */
data class AuthFormState(
    var usernameError: Int? = null,
    var passwordError: Int? = null,
    var nameError: Int? = null,
    var isDataValid: Boolean = false,
    var phoneNumberError: Int? = null,
    var serviceAreaError: Int? = null
)