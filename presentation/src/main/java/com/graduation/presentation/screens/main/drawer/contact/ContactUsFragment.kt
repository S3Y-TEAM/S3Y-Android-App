package com.graduation.presentation.screens.main.drawer.contact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentContactUsBinding
import com.graduation.presentation.screens.BaseFragmentImpl

class ContactUsFragment :
    BaseFragmentImpl<FragmentContactUsBinding>(FragmentContactUsBinding::inflate) {

    override val viewModel: ContactUsViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar(resources.getString(R.string.contact_us))
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