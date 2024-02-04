package com.graduation.presentation.screens.auth.position

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.navigateToWithBundle
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentPositionBinding
import com.graduation.presentation.screens.BaseFragmentImpl

class PositionFragment :
    BaseFragmentImpl<FragmentPositionBinding>(FragmentPositionBinding::inflate) {

    override val viewModel: PositionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()

    }

    override fun setOnClickListener() {
        binding.apply {

            developerButton.setOnClickListener {
                positionSelected(
                    position = "developer"
                )
            }
            skilledButton.setOnClickListener {
                positionSelected(
                    position = "skilled_worker"
                )
            }
            userButton.setOnClickListener {
                positionSelected(
                    position = "user"
                )
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
        navigateToWithBundle(
            R.id.action_positionFragment_to_signUpFragment,
            mapOf("position" to position)
        )
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.background, isContentLight = false, isTransparent = false)
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

}