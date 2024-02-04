package com.graduation.presentation.screens.auth.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentStartBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment :
    BaseFragmentImpl<FragmentStartBinding>(FragmentStartBinding::inflate) {

    override val viewModel: StartViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()

    }

    override fun setOnClickListener() {

        binding.apply {
            startBtn.setOnClickListener {
                navigateTo(R.id.action_startFragment_to_onboardingFragment)
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.main_color, isContentLight = true, isTransparent = false)
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }


}