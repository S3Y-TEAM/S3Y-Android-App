package com.graduation.presentation.screens.auth.national

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.Constants.IMAGE_TYPE
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentNationalDataBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NationalDataFragment :
    BaseFragmentImpl<FragmentNationalDataBinding>(FragmentNationalDataBinding::inflate) {

    override val viewModel: NationalDataViewModel by viewModels()
    private var isNational = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()

        setOnClickListener()
        setAppBar()

    }

    private fun setData() {
        binding.apply {
            nationalImageCard.certificateFile.text = resources.getText(R.string.upload_image)
            personalImageCard.certificateFile.text = resources.getText(R.string.upload_image)
        }
    }

    override fun setOnClickListener() {

        binding.apply {
            nationalButton.setOnClickListener {
                lifecycleScope.launch {
                    nationalButton.loadingDrawable.strokeWidth =
                        nationalButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)
                }
            }
            nationalImageCard.apply {
                certificateFile.setOnClickListener {
                    isNational = true
                    blurViewNational.visibility = View.VISIBLE
                    startIntent()
                }
                saveBtn.setOnClickListener {
                    viewNationalItems(
                        nationalCardVisibility = View.GONE,
                        imageSavedVisibility = View.VISIBLE
                    )
                }
            }
            nationalSaved.editProject.setOnClickListener {
                viewNationalItems(
                    nationalCardVisibility = View.VISIBLE,
                    imageSavedVisibility = View.GONE
                )
            }

            personalImageCard.apply {
                certificateFile.setOnClickListener {
                    isNational = false
                    blurViewNational.visibility = View.VISIBLE
                    startIntent()
                }
                saveBtn.setOnClickListener {
                    viewPersonalItems(
                        personalCardVisibility = View.GONE,
                        imageSavedVisibility = View.VISIBLE
                    )
                }
            }
            personalSaved.editProject.setOnClickListener {
                viewPersonalItems(
                    personalCardVisibility = View.VISIBLE,
                    imageSavedVisibility = View.GONE
                )
            }

            nationalAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            nationalAppBar.appBarSkipBtn.setOnClickListener {
            }
        }
    }

    private fun viewNationalItems(nationalCardVisibility: Int, imageSavedVisibility: Int) {
        binding.apply {
            nationalImageCard.root.visibility = nationalCardVisibility
            nationalSaved.root.visibility = imageSavedVisibility
        }
    }

    private fun viewPersonalItems(personalCardVisibility: Int, imageSavedVisibility: Int) {
        binding.apply {
            personalImageCard.root.visibility = personalCardVisibility
            personalSaved.root.visibility = imageSavedVisibility
        }
    }

    private fun startIntent() {
        val i = Intent().apply {
            type = IMAGE_TYPE
            action = Intent.ACTION_GET_CONTENT
        }
        intentResultLauncher.launch(i)
    }


    private val intentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                binding.blurViewNational.visibility = View.GONE
                val data = result.data
                if (isNational) {
                    Log.d("suzan", data.toString() + "0")
                } else {
                    Log.d("suzan", data.toString() + "1")
                }
            } else
                binding.blurViewNational.visibility = View.GONE
        }


    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.nationalAppBar.appBarTitle.text = resources.getText(R.string.national_data)
    }

    override fun onLoadingStart() {
        binding.nationalButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.nationalButton.complete(true)
    }

    override fun onCancel() {
        binding.nationalButton.cancel()
    }
}