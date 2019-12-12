package com.ut.iot.rooms.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.ut.iot.rooms.R
import com.ut.iot.rooms.api.model.device.DeviceResponse
import com.ut.iot.rooms.api.model.device.UpdateFCMToken
import com.ut.iot.rooms.data.BannerMessage
import com.ut.iot.rooms.data.BannerType
import com.ut.iot.rooms.data.EventBus
import com.ut.iot.rooms.data.model.ResourceLoading
import com.ut.iot.rooms.repo.device.DeviceRepo
import com.ut.iot.rooms.state.StateManager
import com.ut.iot.rooms.ui.auth.AuthActivity
import com.ut.iot.rooms.util.*
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.horizontal_loader.*
import timber.log.Timber
import java.util.*
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

    private lateinit var broadcastManager: LocalBroadcastManager

    private var banner: BannerNotification? = null
    private var networkConnectivityReceiver: NetworkConnectivityReceiver? = null

    abstract fun handleResourceLoading(resourceLoading: ResourceLoading?)

    protected lateinit var disposables: CompositeDisposable

    @Inject
    lateinit var deviceRepo: DeviceRepo
    private var isInternetAvailable = true

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

        broadcastManager = LocalBroadcastManager.getInstance(this)

        broadcastManager.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val hasInternet = intent.getBooleanExtra(NETWORK_AVAILABILITY, true)
                if (hasInternet && !isInternetAvailable) {

                    isInternetAvailable = true
                    banner?.dismiss()
                } else if(!hasInternet) {
                    Timber.d("Has Internet 1 $hasInternet")
                    isInternetAvailable = false
                    showError(getString(R.string.no_internet_text))
                }
            }
        }, IntentFilter(NETWORK_ACTION))

        broadcastManager.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                Timber.d("New push ${intent.extras}")
                showBanner(
                    "success", intent.getStringExtra("title") ?: getString(R.string.app_name),
                    intent.getStringExtra("message") ?: ""
                ) {

                }
                banner?.show()
            }
        }, IntentFilter(PUSH_NOTIFICATION))


        val internetFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        networkConnectivityReceiver = NetworkConnectivityReceiver()
        registerReceiver(networkConnectivityReceiver, internetFilter)

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
                        showError(it.message)
                    }
                    BannerType.SUCCESS -> {
                        showSuccess(it.message)
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
                        banner?.dismiss()
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
        showBanner("error", content = message) { banner?.dismiss() }
    }

    fun showSuccess(message: String) {
        Timber.d("Message $message")
        showBanner("success", content = message) { banner?.dismiss() }
    }


    private fun showBanner(
        type: String,
        title: String = getString(R.string.app_name),
        content: String,
        rightAction: () -> Unit
    ) {
        banner = findViewById(R.id.banner)
        banner?.apply {
            when (type.toLowerCase(Locale.ROOT)) {
                "success" -> setBackgroundResource(R.color.teal_400)
                "error" -> setBackgroundResource(R.color.red_400)
                else -> setBackgroundResource(R.color.colorPrimary)
            }
            bannerTitle = title
            rightButtonText = R.string.ok
            bannerContent = content
            setRightButtonAction(rightAction)
            show()
        }
    }


    override fun onDestroy() {
        try {
            if (networkConnectivityReceiver != null) {
                unregisterReceiver(networkConnectivityReceiver)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        disposables.dispose()
        super.onDestroy()
    }



}