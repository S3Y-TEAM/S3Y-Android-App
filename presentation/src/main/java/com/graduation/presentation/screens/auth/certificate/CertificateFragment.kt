package com.graduation.presentation.screens.auth.certificate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.Constants
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentCertificateBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.project.adapter.ProjectAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CertificateFragment :
    BaseFragmentImpl<FragmentCertificateBinding>(FragmentCertificateBinding::inflate) {

    override val viewModel: CertificateViewModel by viewModels()
    private lateinit var adapterItems: ProjectAdapter
    private var items: MutableList<String> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        setupRV()
    }

    private fun setupRV() {
        adapterItems = ProjectAdapter()
        adapterItems.differ.submitList(items)
        binding.certificateRv.apply {
            adapter = adapterItems
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
        }
    }

    override fun setOnClickListener() {
        binding.apply {
            certificateButton.setOnClickListener {
                lifecycleScope.launch {
                    certificateButton.loadingDrawable.strokeWidth =
                        certificateButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)
                    navigateTo(R.id.action_certificateFragment_to_linkedFragment)
                }
            }

            certificateItem.saveBtn.setOnClickListener {
                items.add("Certificate")
                //...
                binding.certificateRv.adapter!!.notifyDataSetChanged()
                certificateItem.root.visibility = View.GONE

            }
            certificateItem.certificateFile.setOnClickListener {
                showBottomSheet()
            }
            uploadCertificateBtn.setOnClickListener {
                if (certificateItem.root.visibility == View.VISIBLE)
                    Toast.makeText(requireActivity(), "Please complete data", Toast.LENGTH_SHORT)
                        .show()
                else {
                    certificateItem.root.visibility = View.VISIBLE
                }
            }
            certificateAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            certificateAppBar.appBarSkipBtn.setOnClickListener {
                navigateTo(R.id.action_certificateFragment_to_linkedFragment)
            }

        }

    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.certificateAppBar.apply {
            appBarTitle.text = resources.getText(R.string.certificates)
            appBarSkipBtn.visibility = View.VISIBLE
        }
    }

    override fun onLoadingStart() {
        binding.certificateButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.certificateButton.complete(true)
    }

    override fun onCancel() {
        binding.certificateButton.cancel()
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.choose_file_type, null)
        val btnCamera: LinearLayout = view.findViewById(R.id.image_type)
        btnCamera.setOnClickListener {
            startIntent(Constants.IMAGE_TYPE)
            dialog.dismiss()
        }
        val btnGallery: LinearLayout = view.findViewById(R.id.pdf_type)
        btnGallery.setOnClickListener {
            startIntent(Constants.PDF_TYPE)
            dialog.dismiss()
        }
        dialog.setOnCancelListener {
            binding.blurViewCertificate.visibility = View.GONE
        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.dismissWithAnimation = true
        binding.blurViewCertificate.visibility = View.VISIBLE
        dialog.show()
    }

    private fun startIntent(fileType: String) {
        val i = Intent().apply {
            type = fileType
            action = Intent.ACTION_GET_CONTENT
        }
        intentResultLauncher.launch(i)
    }


    private val intentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data
                binding.blurViewCertificate.visibility = View.GONE
                Log.d("suzan", data.toString())
            }else
                binding.blurViewCertificate.visibility = View.GONE

        }



}