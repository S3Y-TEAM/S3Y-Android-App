package com.graduation.presentation.screens.auth.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentSignUpBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragmentImpl<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    override val viewModel: SignUpViewModel by viewModels()

    private lateinit var position: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleData()
        setOnClickListener()
        setAppBar()

    }

    private fun getBundleData() {
        position = arguments?.getString("position").toString()
        Toast.makeText(requireActivity(), position, Toast.LENGTH_SHORT).show()
    }

    override fun setOnClickListener() {

        binding.apply {
            signUpButton.setOnClickListener {
                lifecycleScope.launch {
                    signUpButton.loadingDrawable.strokeWidth = signUpButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)
                    navigateTo(R.id.action_signUpFragment_to_categoriesFragment)
                }
            }
            signUpAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.signUpAppBar.appBarTitle.text = resources.getText(R.string.sign_up_to_s3y)
    }

    override fun onLoadingStart() {
        binding.signUpButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.signUpButton.complete(true)
    }

    override fun onCancel() {
        binding.signUpButton.cancel()
    }

}