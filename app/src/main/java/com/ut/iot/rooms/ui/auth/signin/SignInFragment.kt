package com.ut.iot.rooms.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ut.iot.rooms.R
import com.ut.iot.rooms.api.model.auth.AuthRequest
import com.ut.iot.rooms.data.model.Status
import com.ut.iot.rooms.ui.auth.AuthBaseFragment
import com.ut.iot.rooms.ui.home.HomeActivity
import com.ut.iot.rooms.util.afterTextChanged
import kotlinx.android.synthetic.main.sign_in_fragment.view.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 27/11/2019.
 */
class SignInFragment : AuthBaseFragment() {

    private var authSuccess = false

    @Inject
    lateinit var signInViewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.sign_up.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }


        signInViewModel.authResult.observe(viewLifecycleOwner, Observer {
            Timber.d("It ${it.status}")
            if (it.status == Status.SUCCESS && !authSuccess) {
                authSuccess = true
                Timber.d("Called loginResult")
                val intent = Intent(authActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else if (it.status == Status.ERROR) {
                showAuthError()
                view.sign_in_button.isEnabled = true
                view.sign_up.isEnabled = true
            } else {
                view.sign_in_button.isEnabled = false
                view.sign_up.isEnabled = false
            }
        })

        signInViewModel.authFormState.observe(viewLifecycleOwner, Observer {
            val signInState = it ?: return@Observer

            view.sign_in_button.isEnabled = signInState.isDataValid

            Timber.d("State $signInState")
            if (signInState.usernameError != null) {
                view.email_holder.error = getString(signInState.usernameError!!)
            } else {
                view.email_holder.error = null
            }

            if (signInState.passwordError != null) {
                view.password_holder.error = getString(signInState.passwordError!!)
            } else {
                view.password_holder.error = null
            }


        })

        view.sign_in_button.isEnabled = false

        view.sign_in_button.setOnClickListener {
            loginWithEmailAndPassword(
                view.email_address.text.toString(),
                view.password.text.toString()
            )
        }

        view.email_address.apply {
            afterTextChanged {
                if (it.isBlank() && view.password.text!!.isBlank()) {
                    return@afterTextChanged
                }
                signInViewModel.loginDataChanged(
                    it,
                    view.password.text.toString()
                )
            }
        }

        view.password.apply {
            afterTextChanged {
                //this so we don't do a validation check when the system is restoring things :)
                if (it.isBlank() && view.email_address.text!!.isBlank()) {
                    return@afterTextChanged
                }
                Timber.d("Password After text changed called")
                signInViewModel.loginDataChanged(
                    view.email_address.text.toString(), it
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginWithEmailAndPassword(
                            view.email_address.text.toString(),
                            view.password.text.toString()
                        )
                }
                false
            }
        }

    }


    fun auth(authRequest: AuthRequest) {
        signInViewModel.makeAuthRequest(authRequest)
    }

    private fun loginWithEmailAndPassword(emailAddress: String, password: String) {
        if (emailAddress.isEmpty() || password.isEmpty()) {
            return
        }
        UIUtil.hideKeyboard(authActivity)
        val authRequest = AuthRequest(email = emailAddress, password = password)
        auth(authRequest)
    }

}