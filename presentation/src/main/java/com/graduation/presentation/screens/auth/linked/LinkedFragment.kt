package com.graduation.presentation.screens.auth.linked

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentLinkedBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LinkedFragment : BaseFragmentImpl<FragmentLinkedBinding>(FragmentLinkedBinding::inflate) {

    override val viewModel: LinkedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
    }

    override fun setOnClickListener() {
        binding.apply {
            linkedButton.setOnClickListener {
                lifecycleScope.launch {
                    linkedButton.loadingDrawable.strokeWidth =
                        linkedButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)
                }
            }
            linkedAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            linkedAppBar.appBarSkipBtn.setOnClickListener {
            }

        }

    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.linkedAppBar.apply {
            appBarTitle.text = resources.getText(R.string.linked)
            appBarSkipBtn.visibility = View.VISIBLE
        }
    }

    override fun onLoadingStart() {
        binding.linkedButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.linkedButton.complete(true)
    }

    override fun onCancel() {
        binding.linkedButton.cancel()
    }


}