package com.graduation.presentation.screens.main.drawer.rate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.presentation.databinding.FragmentRateUsBinding
import com.graduation.presentation.screens.BaseFragmentImpl


class RateUsFragment :
    BaseFragmentImpl<FragmentRateUsBinding>(FragmentRateUsBinding::inflate) {

    override val viewModel: RateUsViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("RateUs")

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