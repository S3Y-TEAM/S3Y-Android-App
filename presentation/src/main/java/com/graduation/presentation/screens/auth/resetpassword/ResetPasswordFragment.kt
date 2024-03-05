package com.graduation.presentation.screens.auth.resetpassword

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.hideKeypad
import com.graduation.core.utils.toastMe
import com.graduation.domain.models.auth.resetpassword.ResetPasswordRequest
import com.graduation.presentation.Constants
import com.graduation.presentation.Constants.VALID
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentResetPasswordBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragmentImpl<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {

    override val viewModel: ResetPasswordViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        observation()
        passwordChange()

    }

    private fun passwordChange() {
        binding.apply {
            passwordEdittext.addTextChangedListener { passwordEdittext ->
                viewModel.confirmPassword(
                    password = passwordEdittext.toString(),
                    confirmPassword = binding.confirmPasswordEdittext.text.toString()
                )
            }

            confirmPasswordEdittext.addTextChangedListener { confirmPasswordEdittext ->
                viewModel.confirmPassword(
                    password = binding.passwordEdittext.text.toString(),
                    confirmPassword = confirmPasswordEdittext.toString()
                )
            }
        }
    }


    private fun observation() {
        viewModel.apply {
            password.observe(viewLifecycleOwner) { password ->
                if (password == Constants.VALID)
                    binding.passwordError.visibility = View.GONE
                else {
                    binding.passwordError.visibility = View.VISIBLE
                    binding.passwordError.text = password
                }

            }
            confirmPassword.observe(viewLifecycleOwner) { confirmPassword ->
                if (confirmPassword == Constants.VALID)
                    binding.confirmPasswordError.visibility = View.GONE
                else {
                    binding.confirmPasswordError.visibility = View.VISIBLE
                    binding.confirmPasswordError.text = confirmPassword
                }
            }
            errorPassword.observe(viewLifecycleOwner){error->
                if (error == VALID){
                    Log.d("Suzan" , "Suzan")
                    onComplete(isSuccess = true)
                    navigateTo(R.id.action_resetPasswordFragment_to_passwordUpdatedDialog)
                }else{
                    Log.d("VALID" , error)
                }

            }
        }
    }

    override fun setOnClickListener() {
        binding.apply {
            resetButton.setOnClickListener {
                lifecycleScope.launch {
                    requireActivity().hideKeypad()
                    onLoadingStart()
                    viewModel.sendSuccess()
                    if (viewModel.isSuccess.value == true) {
                        viewModel.savePassword(
                            role = sharedViewModel.role.value.toString(),
                            resetPasswordRequest = ResetPasswordRequest(
                                email = sharedViewModel.emailAddress.value.toString(),
                                newPassword = passwordEdittext.text.toString(),
                                confirmPassword = confirmPasswordEdittext.text.toString()
                            )
                        )
                        //onComplete(isSuccess = true)
                        //navigateTo(R.id.action_resetPasswordFragment_to_passwordUpdatedDialog)
                    } else {
                        onCancel()
                        toastMe(
                            context = requireContext(),
                            message = resources.getText(R.string.password_not_match).toString()
                        )
                    }
                }
            }
            resetAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.resetAppBar.appBarTitle.text = resources.getText(R.string.forget_password_title)
    }

    override fun onLoadingStart() {
        binding.resetButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.resetButton.complete(isSuccess)
    }

    override fun onCancel() {
        binding.resetButton.cancel()
    }


}