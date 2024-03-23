package com.graduation.presentation.screens.main.drawer.about.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentAboutAppBinding
import com.graduation.presentation.databinding.FragmentProfileBinding
import com.graduation.presentation.screens.BaseFragmentImpl

class AboutAppFragment :
    BaseFragmentImpl<FragmentAboutAppBinding>(FragmentAboutAppBinding::inflate) {

    override val viewModel: AboutAppViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("About App")

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