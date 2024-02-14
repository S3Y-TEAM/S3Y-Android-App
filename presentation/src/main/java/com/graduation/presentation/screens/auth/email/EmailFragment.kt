package com.graduation.presentation.screens.auth.email

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.graduation.presentation.databinding.FragmentEmailBinding
import com.graduation.presentation.screens.BaseFragmentImpl

class EmailFragment : BaseFragmentImpl<FragmentEmailBinding>(FragmentEmailBinding::inflate) {

    override val viewModel: EmailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()

    }

    override fun setOnClickListener() {
        TODO("Not yet implemented")
    }

    override fun setAppBar() {
        TODO("Not yet implemented")
    }

    override fun onLoadingStart() {
        TODO("Not yet implemented")
    }

    override fun onComplete(isSuccess: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onCancel() {
        TODO("Not yet implemented")
    }
}