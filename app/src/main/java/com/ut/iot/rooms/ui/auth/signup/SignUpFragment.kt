package com.ut.iot.rooms.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ut.iot.rooms.R
import com.ut.iot.rooms.ui.auth.AuthBaseFragment
import kotlinx.android.synthetic.main.sign_up_fragment.view.*


/**
 * Created by Saeed on 27/11/2019.
 */
class SignUpFragment : AuthBaseFragment() {

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
    }
}