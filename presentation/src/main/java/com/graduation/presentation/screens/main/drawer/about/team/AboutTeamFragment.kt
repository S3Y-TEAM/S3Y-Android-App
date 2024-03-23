package com.graduation.presentation.screens.main.drawer.about.team

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.presentation.databinding.FragmentAboutTeamBinding
import com.graduation.presentation.screens.BaseFragmentImpl

class AboutTeamFragment :
    BaseFragmentImpl<FragmentAboutTeamBinding>(FragmentAboutTeamBinding::inflate) {

    override val viewModel: AboutTeamViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("About Team")

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