package com.graduation.presentation.screens.auth.phone

import android.os.Bundle
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
import com.graduation.domain.models.auth.phone.PhoneOTPRequest
import com.graduation.presentation.Constants
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentPhoneBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.ozcanalasalvar.otp_view.view.OtpView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint

class PhoneFragment :
    BaseFragmentImpl<FragmentPhoneBinding>(FragmentPhoneBinding::inflate) {

    override val viewModel: PhoneNumberViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    var otpCode = 15042

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        observation()

    }

    private fun observation() {
        viewModel.apply {
            phoneError.observe(viewLifecycleOwner) { emailError ->
                if (emailError == Constants.VALID) {
                    binding.apply {
                        phoneOtpCard.root.visibility = View.VISIBLE
                        phoneError.visibility = View.GONE
                    }
                } else {
                    binding.apply {
                        phoneOtpCard.root.visibility = View.GONE
                        phoneError.visibility = View.VISIBLE
                        phoneError.text = emailError
                    }
                }
            }
            phoneOtp.observe(viewLifecycleOwner) { phoneOtpCode ->
                if (phoneOtpCode != null)
                    toastMe(requireContext() , "Code Hint 15042")
            }
            token.observe(viewLifecycleOwner) { token ->
                if (token != null)
                    encryptedSharedPreference.token = token

            }
        }
    }

    override fun setOnClickListener() {

        binding.apply {

            phoneAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }

            phoneSendBtn.setOnClickListener {
                phoneSendBtn.start()
                if (phoneEdittext.text!!.isNotBlank()) {
                    phoneError.visibility = View.GONE
                    callEmailOtp(phoneNumber = phoneEdittext.text.toString())
                    phoneSendBtn.complete(true)
                } else {
                    phoneError.visibility = View.VISIBLE
                    phoneError.text = resources.getText(R.string.please_enter_phone_number)
                    phoneSendBtn.cancel()
                }
            }

            phoneOtpCard.apply {
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
                    callEmailOtp(phoneNumber = phoneEdittext.text.toString())
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
        binding.phoneOtpCard.apply {
            lifecycleScope.launch {
                confirmBtn.apply {
                    loadingDrawable.strokeWidth = confirmBtn.textSize * 0.14f
                    start()
                    sharedViewModel.setPhoneNumber(binding.phoneEdittext.text.toString().toInt())
                    complete(true)
                    delay(500)
                    navigateTo(R.id.action_phoneFragment_to_completeFragment)
                }
            }
        }
    }


    private fun callEmailOtp(phoneNumber: String) {
        requireActivity().hideKeypad()
        viewModel.callPhoneOTP(
            role = sharedViewModel.role.value.toString(),
            phoneOTPRequest = PhoneOTPRequest(
                userName = sharedViewModel.username.value.toString(),
                email =sharedViewModel.emailAddress.value.toString(),
                phone = phoneNumber
            )
        )
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.phoneAppBar.appBarTitle.text = resources.getText(R.string.phone)
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }
}