package com.graduation.presentation.screens.auth.email

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.hideKeypad
import com.graduation.core.utils.toastMe
import com.graduation.domain.models.auth.email.EmailOTPRequest
import com.graduation.presentation.Constants.VALID
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentEmailBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.ozcanalasalvar.otp_view.view.OtpView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint

class EmailFragment : BaseFragmentImpl<FragmentEmailBinding>(FragmentEmailBinding::inflate) {

    override val viewModel: EmailViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    var otpCode = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        observation()

    }

    private fun observation() {
        viewModel.apply {
            emailError.observe(viewLifecycleOwner) { emailError ->
                if (emailError == VALID) {
                    binding.apply {
                        otpCard.root.visibility = View.VISIBLE
                        emailOtpError.visibility = View.GONE
                        emailSendBtn.complete(true)
                    }
                } else {
                    binding.apply {
                        otpCard.root.visibility = View.GONE
                        emailOtpError.visibility = View.VISIBLE
                        emailOtpError.text = emailError
                    }
                }
            }
            emailOtp.observe(viewLifecycleOwner) { emailOtpCode ->
                if (emailOtpCode != null)
                    otpCode = emailOtpCode
            }

            token.observe(viewLifecycleOwner) { token ->
                if (token != null)
                    encryptedSharedPreference.token = token

            }
        }
    }

    override fun setOnClickListener() {

        binding.apply {
            emailAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }

            emailSendBtn.setOnClickListener {
                emailSendBtn.start()
                if (emailEdittext.text!!.isNotBlank()) {
                    emailOtpError.visibility = View.GONE
                    callEmailOtp(emailAddress = emailEdittext.text.toString())
                    //emailSendBtn.complete(true)
                } else {
                    emailOtpError.visibility = View.VISIBLE
                    emailOtpError.text = resources.getText(R.string.please_enter_email)
                    emailSendBtn.cancel()
                }
            }

            otpCard.apply {
                otpDigit.setTextChangeListener(object : OtpView.ChangeListener {
                    override fun onTextChange(value: String, completed: Boolean) {
                        if (completed) {
                            if (value.toInt() == otpCode) compareOTPCode()
                            else toastMe(
                                context = requireContext(),
                                message = resources.getText(R.string.invalid_otp).toString()
                            )
                        }
                    }
                })
                resendCode.setOnClickListener {
                    callEmailOtp(emailAddress = emailEdittext.text.toString())
                }
                confirmBtn.setOnClickListener {
                    confirmBtn.start()
                    toastMe(
                        context = requireContext(),
                        message = resources.getText(R.string.enter_the_otp_code_we_send_you)
                            .toString()
                    )
                    confirmBtn.cancel()

                }
            }
        }
    }

    private fun compareOTPCode() {
        requireActivity().hideKeypad()
        binding.otpCard.apply {
            lifecycleScope.launch {
                confirmBtn.apply {
                    loadingDrawable.strokeWidth = confirmBtn.textSize * 0.14f
                    start()
                    sharedViewModel.setEmailAddress(binding.emailEdittext.text.toString())
                    complete(true)
                    delay(500)
                    navigateTo(R.id.action_emailFragment_to_phoneFragment)
                }
            }
        }
    }

    private fun callEmailOtp(emailAddress: String) {
        requireActivity().hideKeypad()
        Log.d("accessToken", sharedViewModel.role.value.toString())
        Log.d("accessToken", sharedViewModel.username.value.toString())
        Log.d("accessToken", emailAddress)
        viewModel.callEmailOTP(
            role = sharedViewModel.role.value.toString(),
            emailOTPRequest = EmailOTPRequest(
                userName = sharedViewModel.username.value.toString(),
                email = emailAddress
            )
        )
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.emailAppBar.apply {
            appBarTitle.text = resources.getText(R.string.email_address)
        }
        binding.roleDetails.visibility = View.GONE
        binding.spinnerCard.visibility = View.GONE
        binding.spinnerError.visibility = View.GONE
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

}