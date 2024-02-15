package com.graduation.presentation.screens.auth.email

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.base.ui.BaseViewModel
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentEmailBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.ozcanalasalvar.otp_view.view.OtpView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint

class EmailFragment : BaseFragmentImpl<FragmentEmailBinding>(FragmentEmailBinding::inflate) {
    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()

    }

    override fun setOnClickListener() {

        binding.apply {
            emailButton.setOnClickListener {
                lifecycleScope.launch {
                    emailButton.loadingDrawable.strokeWidth =
                        emailButton.textSize * 0.14f
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)
                }
            }

            emailAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }

            emailSendBtn.setOnClickListener {
                otpCard.root.visibility = View.VISIBLE
            }

            otpCard.apply {
                otpDigit.setTextChangeListener(object : OtpView.ChangeListener {
                    override fun onTextChange(value: String, completed: Boolean) {
                        if (completed) {
                            Toast.makeText(requireActivity(), value, Toast.LENGTH_SHORT).show()
                            lifecycleScope.launch {
                                confirmBtn.apply {
                                    loadingDrawable.strokeWidth = confirmBtn.textSize * 0.14f
                                    start()
                                    delay(1600)
                                    complete(true)
                                    delay(1600)
                                }
                            }
                        }
                    }
                })
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.emailAppBar.apply {
            appBarTitle.text = resources.getText(R.string.email_address)
        }
    }

    override fun onLoadingStart() {
        binding.emailButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.emailButton.complete(true)
    }

    override fun onCancel() {
        binding.emailButton.cancel()
    }

}