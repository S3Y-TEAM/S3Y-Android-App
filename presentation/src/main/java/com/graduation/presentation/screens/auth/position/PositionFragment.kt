package com.graduation.presentation.screens.auth.position

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.Constants.DEVELOPER_KEY
import com.graduation.presentation.Constants.SKILLED_KEY
import com.graduation.presentation.Constants.USER_KEY
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentPositionBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PositionFragment :
    BaseFragmentImpl<FragmentPositionBinding>(FragmentPositionBinding::inflate) {

    override val viewModel: PositionViewModel by viewModels()

    private var isDevClicked = false
    private var isWorkerClicked = false
    private var isEmpClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()

    }

    override fun setOnClickListener() {
        binding.apply {

            developerButton.setOnClickListener {
                isDevClicked = true
                isWorkerClicked = false
                isEmpClicked = false
                buttonAction()
            }
            skilledButton.setOnClickListener {
                isWorkerClicked = true
                isDevClicked = false
                isEmpClicked = false
                buttonAction()
            }
            userButton.setOnClickListener {
                isEmpClicked = true
                isDevClicked = false
                isWorkerClicked = false
                buttonAction()

            }
            login.setOnClickListener {
                navigateTo(R.id.action_positionFragment_to_loginFragment)
            }
            onBackPress {
                requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
                    return@addCallback requireActivity().finish()
                }
            }
        }
    }

    private fun positionSelected(position: String) {
        sharedPreferenceHelper.sharedRole = position
        navigateTo(
            R.id.action_positionFragment_to_signUpFragment
        )
    }

    private fun buttonAction() {
        lifecycleScope.launch {
            onLoadingStart()
            delay(1600)
            onComplete(true)
            delay(500)

            if (isDevClicked)
                positionSelected(
                    position = DEVELOPER_KEY
                )
            else if (isWorkerClicked)
                positionSelected(
                    position = SKILLED_KEY
                )
            else if (isEmpClicked)
                positionSelected(
                    position = USER_KEY
                )
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.background, isContentLight = false, isTransparent = false)
    }

    override fun onLoadingStart() {
        if (isDevClicked)
            binding.developerButton.start()
        else if (isWorkerClicked)
            binding.skilledButton.start()
        else if (isEmpClicked)
            binding.userButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        if (isDevClicked)
            binding.developerButton.complete(isSuccess)
        else if (isWorkerClicked)
            binding.skilledButton.complete(isSuccess)
        else if (isEmpClicked)
            binding.userButton.complete(isSuccess)
    }

    override fun onCancel() {
    }

}