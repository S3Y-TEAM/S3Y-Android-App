package com.graduation.presentation.screens.auth.forgetpassword

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.hideKeypad
import com.graduation.core.utils.toastMe
import com.graduation.domain.models.auth.forgetpassword.ForgetPasswordRequest
import com.graduation.presentation.Constants
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentEmailBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem
import com.graduation.presentation.screens.auth.project.adapter.SpinnerAdapter
import com.ozcanalasalvar.otp_view.view.OtpView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment :
    BaseFragmentImpl<FragmentEmailBinding>(FragmentEmailBinding::inflate) {

    override val viewModel: ForgetPasswordViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    var otpCode = 0
    private var role = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        observation()
        setupSpinner()

    }

    private fun observation() {
        viewModel.apply {
            emailError.observe(viewLifecycleOwner) { emailError ->
                if (emailError == Constants.VALID) {
                    binding.apply {
                        otpCard.root.visibility = View.VISIBLE
                        emailOtpError.visibility = View.GONE
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

    private fun setupSpinner() {
        binding.spinner.adapter = SpinnerAdapter(setDummyData())
    }

    private fun setDummyData(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(0, "Select type"),
            OnboardingItem(R.drawable.ic_person, "Developer"),
            OnboardingItem(R.drawable.ic_person, "Skilled Worker"),
            OnboardingItem(R.drawable.ic_person, "User"),
        )
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
                    emailSendBtn.complete(true)
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

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val data = listOf("noting",
                        Constants.DEVELOPER_KEY,
                        Constants.SKILLED_KEY,
                        Constants.USER_KEY
                    )
                    val selectedItem = data[position]
                    role = selectedItem
                    //toastMe(requireContext(), selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when no item is selected
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
                    sharedViewModel.setRole(role = role)
                    complete(true)
                    delay(500)
                    navigateTo(R.id.action_forgetPasswordFragment_to_resetPasswordFragment)
                }
            }
        }
    }

    private fun callEmailOtp(emailAddress: String) {
        requireActivity().hideKeypad()
        viewModel.callEmailOTP(
            role = "dev",
            forgetPasswordRequest = ForgetPasswordRequest(
                email = emailAddress
            )
        )
    }


    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.apply {
            emailAppBar.appBarTitle.text = resources.getText(R.string.forget_password_title)
            emailLogo.setImageResource(R.drawable.forgot_password)
        }
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }


}