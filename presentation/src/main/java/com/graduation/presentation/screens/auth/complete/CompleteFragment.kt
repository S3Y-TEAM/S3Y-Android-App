package com.graduation.presentation.screens.auth.complete

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.startActivity
import com.graduation.core.utils.sharedPrefernces.LocalUser
import com.graduation.domain.models.auth.signup.Category
import com.graduation.domain.models.auth.signup.Link
import com.graduation.domain.models.auth.signup.SignUpRequest
import com.graduation.presentation.databinding.FragmentCompleteBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import com.graduation.presentation.Constants.VALID
import kotlin.random.Random


@AndroidEntryPoint
class CompleteFragment :
    BaseFragmentImpl<FragmentCompleteBinding>(FragmentCompleteBinding::inflate) {

    override val viewModel: CompleteViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    private val category = arrayListOf<Category>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        observation()
        setSignUpData()

    }

    private fun observation() {
        viewModel.apply {
            token.observe(viewLifecycleOwner) { token ->
                if (token != null)
                    encryptedSharedPreference.token = token

            }

            signUpError.observe(viewLifecycleOwner) { signupError ->
                if (signupError == VALID)
                    encryptedSharedPreference.loggedIn = "done"
            }

            categoriesList.observe(viewLifecycleOwner) { response ->
                response?.user?.apply {
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
                }
            }
        }
    }

    private fun setSignUpData() {
        val fullName = (sharedViewModel.fullName.value.toString().split(" "))
        val fName = fullName[0]
        val lName = fullName[1]
        setCategories()
        sharedViewModel.apply {
            viewModel.callSignup(
                role = sharedViewModel.role.value.toString(),
                signUpRequest = SignUpRequest(
                    address = address.value.toString(),
                    categories = category,
                    email = emailAddress.value.toString(),
                    fName = fName,
                    lName = lName,
                    links = listOf(
                        Link(
                            github = sharedViewModel.github.value.toString(),
                            linkedin = sharedViewModel.linkedIn.value.toString()
                        )
                    ),
                    nationalId = setRandomId(),
                    password = password.value.toString(),
                    phoneNumber = "0" + phoneNumber.value.toString(),
                    city = city.value.toString(),
                    country = country.value.toString(),
                    userName = username.value.toString(),
                )
            )
        }
    }

    private fun setRandomId(): String {
        val random = Random(System.currentTimeMillis())
        val randomNumber = StringBuilder()
        repeat(13) {
            randomNumber.append(random.nextInt(10))
        }
        val firstDigit = (1..9).random(random)
        randomNumber.insert(0, firstDigit)
        return randomNumber.toString()
    }

    private fun setCategories() {
        if (sharedViewModel.savedCategories.value != null) {
            for (i in sharedViewModel.savedCategories.value!!) {
                category.add(Category(i))
            }
        }
    }

    override fun setOnClickListener() {
        binding.completeButton.setOnClickListener {
            successSignUp()
        }
    }

    private fun successSignUp() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    override fun setAppBar() {
    }

    override fun onLoadingStart() {
        binding.completeButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.completeButton.complete(isSuccess)
    }

    override fun onCancel() {
        binding.completeButton.cancel()
    }


}