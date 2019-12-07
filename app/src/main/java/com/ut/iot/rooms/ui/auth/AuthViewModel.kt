package com.ut.iot.rooms.ui.auth

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ut.iot.rooms.api.model.auth.AuthRequest
import com.ut.iot.rooms.data.model.Resource
import com.ut.iot.rooms.data.model.UserInfo
import com.ut.iot.rooms.repo.auth.AuthRepo
import com.ut.iot.rooms.util.NullLiveData
import javax.inject.Inject


/**
 * Created by Saeed on 2019-10-19.
 */
open class AuthViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    val _authForm = MutableLiveData<AuthFormState>()
    val authFormState: LiveData<AuthFormState> = _authForm

    private val authRequest: MutableLiveData<AuthRequest> = MutableLiveData()
    val authResult: LiveData<Resource<UserInfo>>

    init {
        authResult = authRequest.switchMap {
            authRequest.value?.let {
                authRepo.auth(it, it.isLogin)
            } ?: NullLiveData.create()
        }
    }

    fun makeAuthRequest(authRequest: AuthRequest) = this.authRequest.postValue(authRequest)


    protected fun isEmailValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    protected fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}