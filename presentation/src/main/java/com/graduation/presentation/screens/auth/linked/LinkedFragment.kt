package com.graduation.presentation.screens.auth.linked

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.hideKeypad
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentLinkedBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint

class LinkedFragment : BaseFragmentImpl<FragmentLinkedBinding>(FragmentLinkedBinding::inflate) {

    override val viewModel: LinkedViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
    }

    override fun setOnClickListener() {
        binding.apply {
            linkedButton.setOnClickListener {
                lifecycleScope.launch {
                    requireActivity().hideKeypad()
                    onLoadingStart()
                    if (linkedinEdittext.text.isNullOrBlank()) {
                        Toast.makeText(requireContext(), "Enter LinkedIn", Toast.LENGTH_SHORT)
                            .show()
                        onCancel()
                    } else if (githubEdittext.text.isNullOrBlank()) {
                        Toast.makeText(requireContext(), "Enter GitHub", Toast.LENGTH_SHORT)
                            .show()
                        onCancel()
                    } else if (linkedinEdittext.text.toString()
                            .isNotEmpty() && githubEdittext.text.toString().isNotEmpty()
                    ) {
                        sharedViewModel.setGitHub(githubEdittext.text.toString())
                        sharedViewModel.setLinkedIn(linkedinEdittext.text.toString())
                        onComplete(true)
                        delay(500)
                        navigateTo(R.id.action_linkedFragment_to_nationalDataFragment)
                    }
                }
            }
            linkedAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            linkedAppBar.appBarSkipBtn.setOnClickListener {
                navigateTo(R.id.action_linkedFragment_to_nationalDataFragment)
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
        binding.apply {
            linkedButton.loadingDrawable.strokeWidth =
                linkedButton.textSize * 0.14f;
            linkedButton.start()
        }
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.linkedButton.complete(true)
    }

    override fun onCancel() {
        binding.linkedButton.cancel()
    }
}