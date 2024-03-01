package com.graduation.presentation.screens.auth.position

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.graduation.core.base.ui.SharedViewModel
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
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()

    }

    override fun setOnClickListener() {
        binding.apply {

            developerButton.apply {
                setOnClickListener {
                    loadingDrawable.strokeWidth = textSize * 0.14f
                    start()
                    complete(true)
                    buttonAction(DEVELOPER_KEY)
                }
            }
            skilledButton.apply {
                setOnClickListener {
                    loadingDrawable.strokeWidth = textSize * 0.14f
                    start()
                    complete(true)
                    buttonAction(SKILLED_KEY)
                }
            }
            userButton.apply {
                setOnClickListener {
                    loadingDrawable.strokeWidth = textSize * 0.14f
                    start()
                    complete(true)
                    buttonAction(USER_KEY)
                }

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
        sharedViewModel.setRole(position)
        navigateTo(
            R.id.action_positionFragment_to_signUpFragment
        )
    }

    private fun buttonAction(position: String) {
        lifecycleScope.launch {

            positionSelected(
                position = position
            )
        }
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