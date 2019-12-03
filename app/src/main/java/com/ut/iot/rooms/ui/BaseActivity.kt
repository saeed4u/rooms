package com.ut.iot.rooms.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.ut.iot.rooms.api.model.device.DeviceResponse
import com.ut.iot.rooms.api.model.device.UpdateFCMToken
import com.ut.iot.rooms.data.BannerMessage
import com.ut.iot.rooms.data.BannerType
import com.ut.iot.rooms.data.EventBus
import com.ut.iot.rooms.data.model.ResourceLoading
import com.ut.iot.rooms.repo.device.DeviceRepo
import com.ut.iot.rooms.state.StateManager
import com.ut.iot.rooms.ui.auth.AuthActivity
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.horizontal_loader.*
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 27/11/2019.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    protected fun showLoader() {
        loader?.visibility = View.VISIBLE
    }

    protected fun hideLoader() {
        loader?.visibility = View.GONE
    }

    @Inject
    lateinit var stateManager: StateManager

    abstract fun handleResourceLoading(resourceLoading: ResourceLoading?)

    protected lateinit var disposables: CompositeDisposable

    @Inject
    lateinit var deviceRepo: DeviceRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        disposables = CompositeDisposable()
        disposables.addAll(
            listenForResourceLoading(),
            listenForDeviceRegistration(),
            listenForNewFCMToken(),
            listenForApiMessage()
        )
    }


    private fun updateFCMToken(token: UpdateFCMToken) {
        deviceRepo.updateFcmToken(token).observe(this, Observer {
            Timber.d("FCM token updated")
        })
    }

    private fun updateFCMTokenOnServer() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = UpdateFCMToken(task.result?.token)
                updateFCMToken(token)

            })
    }


    private fun listenForApiMessage(): Disposable {
        return EventBus.listen(BannerMessage::class.java)
            .subscribe({
                when (it.bannerType) {
                    BannerType.ERROR -> {
                        if (it.statusCode == "err.request.unauthorised") {
                            toAuthActivity()
                            return@subscribe
                        }
                        // showError(it.message)
                    }
                    BannerType.SUCCESS -> {
                        // showSuccess(it.message)
                    }
                }
            }, {
                it.printStackTrace()
            })
    }


    protected fun toAuthActivity() {
        stateManager.saveAuthToken("")
        stateManager.saveUserId(0)
        stateManager.setLoggedInState(false)
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    private fun listenForResourceLoading(): Disposable {
        return EventBus.listen(ResourceLoading::class.java)
            .subscribe({
                handleResourceLoading(it)
                when (it) {
                    ResourceLoading.LOADING -> {
                        Timber.d("Loading!!")
                        showLoader()
                    }
                    ResourceLoading.DONE -> {
                        hideLoader()
                    }
                    null -> hideLoader()
                }
            }, {
                handleResourceLoading(null)
                hideLoader()
            })
    }

    private fun listenForDeviceRegistration(): Disposable {
        return EventBus.listen(DeviceResponse::class.java)
            .subscribe({
                Timber.d("Device response $it")
                updateFCMTokenOnServer()
            }, {
                it.printStackTrace()
            })
    }

    private fun listenForNewFCMToken(): Disposable {
        return EventBus.listen(UpdateFCMToken::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateFCMToken(it)
            }, {
                it.printStackTrace()
            })
    }

    fun showError(message: String) {
        //showBanner("error", content = message) { banner?.dismiss() }
    }


}