package com.ut.iot.rooms.ui.auth.signin

import com.ut.iot.rooms.R
import com.ut.iot.rooms.repo.auth.AuthRepo
import com.ut.iot.rooms.ui.auth.AuthFormState
import com.ut.iot.rooms.ui.auth.AuthViewModel
import javax.inject.Inject


/**
 * Created by Saeed on 2019-10-17.
 */
class SignInViewModel @Inject constructor(authRepo: AuthRepo) :
    AuthViewModel(authRepo) {

    fun loginDataChanged(username: String, password: String) {
        var canSignIn = true
        val formState = AuthFormState()
        if (!isEmailValid(username)) {
            canSignIn = false
            formState.usernameError = R.string.invalid_username
        } else {
            formState.usernameError = null
        }

        if (!isPasswordValid(password)) {
            canSignIn = false
            formState.passwordError = R.string.invalid_password
        } else {
            formState.passwordError = null
        }

        formState.isDataValid = canSignIn
        _authForm.value = formState
    }

}