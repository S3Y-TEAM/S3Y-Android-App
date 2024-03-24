package com.graduation.presentation.screens.main.drawer.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.presentation.databinding.FragmentSettingBinding
import com.graduation.presentation.screens.BaseFragmentImpl

class SettingFragment :
    BaseFragmentImpl<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override val viewModel: SettingViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("Setting")
    }

    override fun setOnClickListener() {
    }

    override fun setAppBar() {
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }
}
