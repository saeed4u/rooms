package com.ut.iot.rooms.ui.auth.signup

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
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 27/11/2019.
 */
class SignUpFragment : AuthBaseFragment() {

    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.sign_in.setOnClickListener {
            findNavController().popBackStack()
        }


        signUpViewModel.authResult.observe(viewLifecycleOwner, Observer {
            when {
                it.status == Status.SUCCESS -> {
                    Timber.d("Called loginResult")
                    val intent = Intent(authActivity, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                it.status == Status.ERROR -> {
                    showAuthError()
                    handleViewState(true)
                }
                else -> {
                    handleViewState(false)
                }
            }
        })

        signUpViewModel.authFormState.observe(viewLifecycleOwner, Observer {
            val signUpState = it ?: return@Observer
            view.signup_button.isEnabled = signUpState.isDataValid

            Timber.d("State $signUpState")

            if (signUpState.nameError != null) {
                name_holder.error = getString(signUpState.nameError!!)
            } else {
                name_holder.error = null
            }

            if (signUpState.usernameError != null) {
                email_holder.error = getString(signUpState.usernameError!!)
            } else {
                email_holder.error = null
            }

            if (signUpState.passwordError != null) {
                password_holder.error = getString(signUpState.passwordError!!)
            } else {
                password_holder.error = null
            }
        })

        view.name.apply {
            afterTextChanged {
                signUpViewModel.signUpDataChanged(
                    it,
                    view.email.text.toString(),
                    view.password.text.toString()
                )
            }
        }
        view.email.apply {
            afterTextChanged {
                signUpViewModel.signUpDataChanged(
                    view.name.text.toString(),
                    it,
                    view.password.text.toString()
                )
            }
        }
        view.password.apply {
            afterTextChanged {
                signUpViewModel.signUpDataChanged(
                    view.name.text.toString(),
                    view.email.text.toString(), it
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signUp(
                            view.name.text.toString(),
                            view.email.text.toString(),
                            view.password.text.toString()
                        )
                }
                false
            }
        }
        view.signup_button.setOnClickListener {
            signUp(
                view.name.text.toString(),
                view.email.text.toString(),
                view.password.text.toString()
            )
        }

    }

    private fun handleViewState(isEnabled: Boolean) {
        view!!.name.isEnabled = isEnabled
        view!!.email.isEnabled = isEnabled
        view!!.password.isEnabled = isEnabled
        view!!.signup_button.isEnabled = isEnabled
        view!!.sign_in.isEnabled = isEnabled
    }

    private fun signUp(name: String, email: String, password: String) {

        UIUtil.hideKeyboard(authActivity)
        val auth = AuthRequest(
            name,
            email,
            password, false
        )
        signUpViewModel.makeAuthRequest(auth)
    }

}