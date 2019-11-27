package com.ut.iot.rooms.di.modules.sub

import com.ut.iot.rooms.ui.auth.signin.SignInFragment
import com.ut.iot.rooms.ui.auth.signup.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Saeed on 27/11/2019.
 */
@Module
abstract class AuthFragmentModule {

    @ContributesAndroidInjector
    abstract fun providesSignInFragment(): SignInFragment


    @ContributesAndroidInjector
    abstract fun providesSignUpFragment(): SignUpFragment

}