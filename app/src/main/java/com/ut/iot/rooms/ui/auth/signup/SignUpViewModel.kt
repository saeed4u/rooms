package com.ut.iot.rooms.ui.auth.signup

import com.ut.iot.rooms.R
import com.ut.iot.rooms.repo.auth.AuthRepo
import com.ut.iot.rooms.ui.auth.AuthFormState
import com.ut.iot.rooms.ui.auth.AuthViewModel
import javax.inject.Inject


/**
 * Created by Saeed on 28/11/2019.
 */
class SignUpViewModel @Inject constructor(authRepo: AuthRepo) :
    AuthViewModel(authRepo) {

    fun signUpDataChanged(name: String, username: String, password: String) {

        var canSignUp = true
        val formState = AuthFormState()

        if (name.isBlank()) {
            formState.nameError = R.string.invalid_name
        } else {
            formState.nameError = null
        }

        if (!isEmailValid(username)) {
            canSignUp = false
            formState.usernameError = R.string.invalid_username
        } else {
            formState.usernameError = null
        }

        if (!isPasswordValid(password)) {
            canSignUp = false
            formState.passwordError = R.string.invalid_password
        } else {
            formState.passwordError = null
        }

        formState.isDataValid = canSignUp
        _authForm.value = formState
    }

}