package com.graduation.presentation.screens.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentLoginBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragmentImpl<FragmentLoginBinding>(FragmentLoginBinding::inflate) {


    override val viewModel: LoginViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()

    }

    override fun setOnClickListener() {

        binding.apply {
            loginButton.setOnClickListener {
                lifecycleScope.launch {
                    loginButton.loadingDrawable.strokeWidth = loginButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(1600)
                }
            }

            signup.setOnClickListener {
                findNavController().navigateUp()
            }
            loginAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.loginAppBar.appBarTitle.text = resources.getText(R.string.welcome_to_s3y)
    }

    override fun onLoadingStart() {
        binding.loginButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.loginButton.complete(true)
    }

    override fun onCancel() {
        binding.loginButton.cancel()
    }

}

//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //bars setup
//        changeStatusBarColor(R.color.white, false)
//        changeNavigationBar(R.color.white, false, isTransparent = false)
//
//        //requests
//        if (viewModel.countries.value.isEmpty()) {
//            viewModel.getCountries()
//        }
//
//        //observers
//        observeLogin()
//        observeLoginButton()
//        observeCountries(
//            phoneEt = binding.phoneEt,
//            countryPicker = null,
//            onSuccessAndNotEmpty = { response ->
//                //get the first country
//                viewModel.apply {
//                    onCountryCodeChanged(response?.data?.countries?.get(0)?.country_code ?: "")
//                    phoneMaxLength.value = response?.data?.countries?.get(0)?.max_number ?: 0
//                }
//                sharedPreferenceHelper.saveData(
//                    "currentCountry",
//                    response?.data?.countries?.get(0)?.id ?: 0
//                )
//            },
//            onCountryClicked = { country ->
//                //get the selected country
//                viewModel.apply {
//                    onCountryCodeChanged(country.country_code ?: "")
//                    phoneMaxLength.value = country.max_number ?: 0
//                }
//
//                binding.phoneEt.text = ""
//
//                //open keyboard
//                showKeyboard(binding.phoneEt)
//            }
//        )
//
//        //skip
//        binding.skipTv.setOnClickListener { openAppInGuestMode() }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        //when return back set alpha to 1
//        resetAnimation()
//    }
//
//    /** observers **/
//    private fun observeLogin() {
//        viewModel.sendOtpResponse.awareCollectWithReduce(
//            onAny = {
//                binding.loginBtn.isLoading(it.isLoading)
//            }, onSuccessSingleTime = {
//                //go to otpFragment
//                outerAnimation {
//                    navigateToWithBundle(
//                        R.id.action_loginFragment_to_otpFragment, mapOf(
//                            CHECK_OTP_KEY to Constants.CHECK_OTP_LOGIN,
//                            OTP_PHONE_NUMBER to binding.phoneEt.text,
//                            OTP_COUNTRY_ID to viewModel.selectedCountryId.value,
//                        )
//                    )
//                }
//            }, onUnHandledError = { msg, errors ->
//                binding.phoneEt.setError(errors?.phone ?: "")
//                if (errors == null) {
//                    showErrorToast(msg ?: getString(R.string.failed_to_login))
//                }
//
//                //open keyboard
//                showKeyboard(binding.phoneEt)
//            }
//        )
//    }
//
//    private fun observeLoginButton() {
//        //phone
//        binding.phoneEt.setOnTextChange { _, length ->
//            viewModel.onPhoneChange(length, viewModel.phoneMaxLength.value)
//        }
//
//        //maxLength
//        viewModel.phoneMaxLength.awareCollect {
//            viewModel.onPhoneChange(binding.phoneEt.text.length, it)
//        }
//
//        //button
//        viewModel.loginButtonState.awareCollect { state ->
//            if (state.isAllDataEntered) {
//                binding.loginBtn.setButtonStyle(MainButtonStyle.Enabled)
//                binding.loginBtn.setOnClickListener { login() }
//            } else {
//                binding.loginBtn.setButtonStyle(MainButtonStyle.Disabled)
//                binding.loginBtn.setOnClickListener(null)
//            }
//        }
//    }
//
//    /** clicks **/
//    private fun login() {
//        //keyboard
//        hideKeyBoard()
//
//        //validation
//        doIfLoginInputsValid(
//            binding.phoneEt.text,
//            viewModel.selectedCountryId.value,
//        ) {
//            viewModel.sendOtp(
//                SendOtpRequest(
//                    binding.phoneEt.text,
//                    viewModel.selectedCountryId.value,
//                )
//            )
//        }
//    }
//
//    private fun openAppInGuestMode() {
//        startActivityAndClearStack(MainActivity::class.java)
//    }
//
//    /** animation **/
//    private fun resetAnimation() {
//        binding.apply {
//            languageTv.resetView()
//            arrowDownIv.resetView()
//            titleTv.resetView()
//            descriptionTv.resetView()
//            skipTv.resetView()
//            phoneEt.resetView()
//            loginBtn.resetView()
//        }
//    }
//
//    private fun outerAnimation(doAfter: () -> Unit) {
//        lifecycleScope.launch {
//            binding.apply {
//                languageTv.animate().alpha(0f)
//                arrowDownIv.animate().alpha(0f)
//
//                titleTv.slideLeft(100)
//                descriptionTv.slideLeft(200)
//                skipTv.animate().alpha(0f).duration = 100
//
//                phoneEt.slideLeft(300)
//                loginBtn.slideLeft(300)
//
//                delay(400)
//                doAfter.invoke()
//            }
//        }
//    }
//
//    private fun View.slideLeft(duration: Long) {
//        animate().translationX(-100f).duration = duration
//        animate().alpha(0f).duration = duration + 100
//    }
//
//    private fun View.resetView() {
//        alpha = 1f
//        translationX = 0f
//    }
//
//}