package com.ut.iot.rooms.ui.auth

import android.content.Context
import com.ut.iot.rooms.ui.BaseFragment


/**
 * Created by Saeed on 27/11/2019.
 */
abstract class AuthBaseFragment: BaseFragment() {

    lateinit var authActivity: AuthActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authActivity = context as AuthActivity
    }

}