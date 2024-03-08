package com.graduation.presentation.screens.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.sharedPrefernces.LocalUser
import com.graduation.core.utils.toastMe
import com.graduation.domain.models.auth.login.LoginRequest
import com.graduation.presentation.Constants
import com.graduation.presentation.Constants.DEVELOPER_KEY
import com.graduation.presentation.Constants.SKILLED_KEY
import com.graduation.presentation.Constants.USER_KEY
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentLoginBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem
import com.graduation.presentation.screens.auth.project.adapter.SpinnerAdapter
import com.graduation.presentation.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragmentImpl<FragmentLoginBinding>(FragmentLoginBinding::inflate) {


    override val viewModel: LoginViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    private var role = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        observation()
        setupSpinner()

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


    private fun observation() {
        viewModel.apply {
            loginError.observe(viewLifecycleOwner) { loginError ->
                if (loginError != null) {
                    if (loginError == Constants.VALID) {
                        binding.apply {
                            emailError.visibility = View.GONE
                            passwordError.visibility = View.GONE
                            binding.loginButton.complete(true)
                        }
                    } else {
                        binding.apply {
                            loginButton.cancel()
                            emailError.visibility = View.VISIBLE
                            emailError.text = loginError
                            passwordError.visibility = View.VISIBLE
                            passwordError.text = loginError
                        }
                    }
                }
            }
            login.observe(viewLifecycleOwner) { login ->
                login?.apply {
                    encryptedSharedPreference.userData = LocalUser(
                        address = address,
                        city = city,
                        country = country,
                        email = email,
                        fName = fName,
                        id = id,
                        lName = lName,
                        nationalId = nationalId,
                        personalImage = personalImage,
                        phoneNumber = phoneNumber,
                        userName = userName,
                        verified = verified
                    )
                    encryptedSharedPreference.loggedIn = "done"
                    setIsUserDataSaved(isUserDataSaved = true)
                }
            }

            token.observe(viewLifecycleOwner) { token ->
                if (token != null) {
                    encryptedSharedPreference.token = token
                    setIsTokenSaved(isTokenSaved = true)
                }
            }

            isSuccess.observe(viewLifecycleOwner) { isSuccess ->
                if (isSuccess) {
                    successLogin()
                }
            }
        }
    }

    private fun successLogin() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    override fun setOnClickListener() {

        binding.apply {
            loginButton.setOnClickListener {
                lifecycleScope.launch {
                    if (emailEdittext.text.isNullOrBlank())
                        toastMe(
                            context = requireContext(),
                            message = resources.getText(R.string.please_enter_email).toString()
                        )
                    else if (passwordEdittext.text.isNullOrBlank())
                        toastMe(
                            context = requireContext(),
                            message = resources.getText(R.string.please_enter_password).toString()
                        )
                    else if (role == "" || role == "noting")
                        toastMe(
                            context = requireContext(),
                            message = resources.getText(R.string.please_select_role).toString()
                        )
                    else {
                        onLoadingStart()
                        viewModel.callLogin(
                            role = role,
                            LoginRequest(
                                email = emailEdittext.text.toString(),
                                password = passwordEdittext.text.toString()
                            )
                        )
                    }
                }
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val data = listOf("noting", DEVELOPER_KEY, SKILLED_KEY, USER_KEY)
                    val selectedItem = data[position]
                    role = selectedItem
                    //toastMe(requireContext(), selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when no item is selected
                }
            }


            signup.setOnClickListener {
                findNavController().navigateUp()
            }
            forgetPassword.setOnClickListener {
                navigateTo(R.id.action_loginFragment_to_forgetPasswordFragment)
            }
            loginAppBar.appBarBackArrow.setOnClickListener {
                navigateTo(R.id.action_loginFragment_to_positionFragment)
            }
            onBackPress {
                requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
                    return@addCallback navigateTo(R.id.action_loginFragment_to_positionFragment)
                }
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.loginAppBar.appBarTitle.text = resources.getText(R.string.welcome_to_s3y)
    }

    override fun onLoadingStart() {
        binding.apply {
            loginButton.loadingDrawable.strokeWidth = loginButton.textSize * 0.14f
            loginButton.start()
        }
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