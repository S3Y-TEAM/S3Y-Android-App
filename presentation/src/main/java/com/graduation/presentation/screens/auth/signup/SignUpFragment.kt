package com.graduation.presentation.screens.auth.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.domain.models.auth.Auth.UserNameRequest
import com.graduation.presentation.Constants.VALID
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentSignUpBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragmentImpl<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    override val viewModel: SignUpViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    var sendRole = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observation()
        setOnClickListener()
        setAppBar()
        userNameChange()
    }

    private fun observation() {
        viewModel.apply {
            fullName.observe(viewLifecycleOwner) { fullName ->
                if (fullName == VALID)
                    binding.fullNameError.visibility = View.GONE
                else {
                    binding.fullNameError.visibility = View.VISIBLE
                    binding.fullNameError.text = fullName
                }
            }
            userName.observe(viewLifecycleOwner) { userName ->
                if (userName == VALID)
                    binding.userNameError.visibility = View.GONE
                else {
                    binding.userNameError.visibility = View.VISIBLE
                    binding.userNameError.text = userName
                }
            }
            password.observe(viewLifecycleOwner) { password ->
                if (password == VALID)
                    binding.passwordError.visibility = View.GONE
                else {
                    binding.passwordError.visibility = View.VISIBLE
                    binding.passwordError.text = password

                }

            }
            confirmPassword.observe(viewLifecycleOwner) { confirmPassword ->
                if (confirmPassword == VALID)
                    binding.confirmPasswordError.visibility = View.GONE
                else {
                    binding.confirmPasswordError.visibility = View.VISIBLE
                    binding.confirmPasswordError.text = confirmPassword
                }
            }

            token.observe(viewLifecycleOwner) { token ->
                if (token != null)
                    encryptedSharedPreference.token = token
            }
        }
        sharedViewModel.role.observe(viewLifecycleOwner) { role ->
            if (role != null)
                sendRole = role
            else
                Log.d("LOL" , "NULL")
        }
    }

    private fun userNameChange() {
        binding.apply {
            userNameEdittext.addTextChangedListener { userNameEdittext ->
                if (sendRole != "")
                    viewModel.sendUsername(
                        role = sendRole,
                        userNameRequest = UserNameRequest(userNameEdittext.toString())
                    )
            }
            fullNameEdittext.addTextChangedListener { fullNameEdittext ->
                viewModel.fullNameEntered(fullNameEdittext.toString())
            }

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

    override fun setOnClickListener() {
        binding.apply {
            signUpButton.setOnClickListener {
                lifecycleScope.launch {
                    onLoadingStart()
                    delay(500)
                    viewModel.sendSuccess()
                    if (viewModel.isSuccess.value == true) {
                        onComplete(true)
                        saveData()
                        navigateTo(R.id.action_signUpFragment_to_categoriesFragment)
                    } else {
                        onCancel()
                        Toast.makeText(
                            requireContext(),
                            "Please, Complete all Data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            signUpAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun saveData() {
        sharedViewModel.apply {
            binding.apply {
                setUsername(username = userNameEdittext.text.toString())
                setFullName(fullName = fullNameEdittext.text.toString())
                setPassword(password = passwordEdittext.text.toString())
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.signUpAppBar.appBarTitle.text = resources.getText(R.string.sign_up_to_s3y)
    }

    override fun onLoadingStart() {
        binding.signUpButton.loadingDrawable.strokeWidth = binding.signUpButton.textSize * 0.14f;
        binding.signUpButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.signUpButton.complete(isSuccess)
    }

    override fun onCancel() {
        binding.signUpButton.cancel()
    }

}