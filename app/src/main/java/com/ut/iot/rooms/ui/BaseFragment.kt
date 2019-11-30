package com.ut.iot.rooms.ui

import android.content.Context
import dagger.android.support.DaggerFragment


/**
 * Created by Saeed on 27/11/2019.
 */
abstract class BaseFragment: DaggerFragment() {

    protected lateinit var baseActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as BaseActivity
    }

}