package com.graduation.presentation.screens.auth.project

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
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
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.Constants.IMAGE_TYPE
import com.graduation.presentation.Constants.PDF_TYPE
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentProjectBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem
import com.graduation.presentation.screens.auth.project.adapter.ProjectAdapter
import com.graduation.presentation.screens.auth.project.adapter.SpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class ProjectFragment : BaseFragmentImpl<FragmentProjectBinding>(FragmentProjectBinding::inflate) {

    override val viewModel: ProjectViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var adapterItems: ProjectAdapter
    private var items: MutableList<String> = mutableListOf()
    private lateinit var selectedPdfFile: File

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        setOnClickListener()
        setAppBar()
        setupRV()
    }

    private fun setupSpinner() {
        binding.projectItem.spinner.adapter = SpinnerAdapter(setDummyData())
    }

    private fun setDummyData(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(0, "Select type"),
            OnboardingItem(R.drawable.ic_edit, "Edit"),
            OnboardingItem(R.drawable.ic_id, "ID"),
            OnboardingItem(R.drawable.ic_github, "GitHub"),
            OnboardingItem(R.drawable.ic_linkedin, "LinkedIn"),
        )
    }

    private fun setupRV() {
        adapterItems = ProjectAdapter()
        adapterItems.differ.submitList(items)
        binding.projectRv.apply {
            adapter = adapterItems
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
        }
    }

    override fun setOnClickListener() {
        binding.apply {
            projectButton.setOnClickListener {
                lifecycleScope.launch {
                    projectButton.loadingDrawable.strokeWidth =
                        projectButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)
                    navigateTo(R.id.action_projectFragment_to_certificateFragment)
                }
            }

            projectItem.saveBtn.setOnClickListener {
                items.add("S3Y Application")
                //...
                //binding.projectRv.adapter!!.notifyDataSetChanged()
                adapterItems.differ.submitList(items)
                projectItem.root.visibility = View.GONE

            }
            projectItem.projectFile.setOnClickListener {
                showBottomSheet()
            }
            uploadProjectBtn.setOnClickListener {
                if (projectItem.root.visibility == View.VISIBLE)
                    Toast.makeText(requireActivity(), "Please complete data", Toast.LENGTH_SHORT)
                        .show()
                else {
                    projectItem.root.visibility = View.VISIBLE
                }
            }
            projectAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            projectAppBar.appBarSkipBtn.setOnClickListener {
                navigateTo(R.id.action_projectFragment_to_certificateFragment)
            }

        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.choose_file_type, null)
        val btnCamera: LinearLayout = view.findViewById(R.id.image_type)
        btnCamera.setOnClickListener {
            startIntent(IMAGE_TYPE)
            dialog.dismiss()
        }
        val btnGallery: LinearLayout = view.findViewById(R.id.pdf_type)
        btnGallery.setOnClickListener {
            startIntent(PDF_TYPE)
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.setOnCancelListener {
            binding.blurView.visibility = View.GONE
        }
        dialog.setContentView(view)
        dialog.dismissWithAnimation = true
        binding.blurView.visibility = View.VISIBLE
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
                val data = result.data!!.data
                binding.blurView.visibility = View.GONE
//                val inputStream = contentResolver.openInputStream(data)
//                val fileData = inputStream?.readBytes()
//
//                val encodedData = Base64.encodeToString(fileData, Base64.DEFAULT)
//                val requestBody = MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart(
//                        "pdf",
//                        "filename.pdf",
//                        RequestBody.create("application/pdf".toMediaTypeOrNull(), fileData)
//                    )
//                    .build()
                data?.let {
                    val c = FileUtils.getPath(requireContext(), data)
                    if (c != null) {
                        selectedPdfFile = File(c)
                        Log.d("pdf", selectedPdfFile.toString())
                        Log.d("pdf", selectedPdfFile.name.toString())
                        Log.d("pdf", selectedPdfFile.absolutePath.toString())

                    } else
                        Log.d("pdf", "null")
                }


//                data?.let {
//                    val inputStream = contentResolver.openInputStream(uri)
//                    inputStream?.use { input ->
//                        val tempFile = createTempFile("temp_pdf", ".pdf")
//                        tempFile.outputStream().use { output ->
//                            input.copyTo(output)
//                        }
//                        selectedPdfFile = tempFile
//                        uploadPdfFile(selectedPdfFile)
//                    }
//                }


                Log.d("suzan", data.toString())
            } else
                binding.blurView.visibility = View.GONE

        }


    object FileUtils {

        fun getPath(context: Context, uri: Uri): String? {
            var cursor: Cursor? = null
            return try {
                val projection = arrayOf(OpenableColumns.DISPLAY_NAME)
                cursor = context.contentResolver.query(uri, projection, null, null, null)
                cursor?.let {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (it.moveToFirst()) {
                        val fileName = it.getString(nameIndex)
                        val file = File(context.cacheDir, fileName)
                        return if (file.exists()) {
                            file.delete()
                            null
                        } else {
                            file.absolutePath
                        }
                    }
                }
                null
            } finally {
                cursor?.close()
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.projectAppBar.apply {
            appBarTitle.text = resources.getText(R.string.project)
            appBarSkipBtn.visibility = View.VISIBLE
        }
    }

    override fun onLoadingStart() {
        binding.projectButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.projectButton.complete(true)
    }

    override fun onCancel() {
        binding.projectButton.cancel()
    }

}