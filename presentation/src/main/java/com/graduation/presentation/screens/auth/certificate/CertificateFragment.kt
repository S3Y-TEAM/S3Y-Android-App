package com.graduation.presentation.screens.auth.certificate

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.toastMe
import com.graduation.presentation.Constants
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentCertificateBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.project.ProjectFragment
import com.graduation.presentation.screens.auth.project.adapter.ProjectAdapter
import com.graduation.presentation.screens.auth.utils.FileUtils
import com.graduation.presentation.screens.auth.utils.imageRefactored
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class CertificateFragment :
    BaseFragmentImpl<FragmentCertificateBinding>(FragmentCertificateBinding::inflate) {

    override val viewModel: CertificateViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var adapterItems: ProjectAdapter
    private var items: MutableList<String> = mutableListOf()
    private lateinit var selectedPdfFile: File
    private var isImage = false
    private var imageName = ""
    private var fileName = ""


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
                if (fileName == "" && imageName == "") {
                    certificateItem.root.visibility = View.GONE
                } else if (fileName != "") {
                    items.add(fileName)
                    binding.certificateRv.adapter!!.notifyItemInserted(items.size - 1)
                    certificateItem.root.visibility = View.GONE
                    fileName = ""
                } else {
                    items.add(imageName)
                    binding.certificateRv.adapter!!.notifyItemInserted(items.size - 1)
                    certificateItem.root.visibility = View.GONE
                    imageName = ""
                }


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
        binding.certificateItem.certificateFile.text = resources.getText(R.string.upload_image)
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
            isImage = true
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
                if (isImage) {
                    binding.blurViewCertificate.visibility = View.GONE
                    val m = imageRefactored(requireContext(), result.data!!.data!!)
                    if (m != null) {
                        imageName = m.name
                        binding.certificateItem.certificateFile.text = m.name
                    }

                } else {
                    val data = result.data!!.data
                    binding.blurViewCertificate.visibility = View.GONE
                    data?.let {
                        val c = FileUtils.getPath(requireContext(), data)
                        if (c != null) {
                            selectedPdfFile = File(c)
                            fileName = selectedPdfFile.name
                        } else
                            Log.d("pdf", "null")
                    }
                    Log.d("suzan", data.toString())
                }
            } else
                binding.blurViewCertificate.visibility = View.GONE
        }
}