package com.graduation.presentation.screens.main.user.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.navigateToWithBundle
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.toastMe
import com.graduation.presentation.Constants
import com.graduation.presentation.Constants.USER_KEY
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentAddTaskDetailsBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem
import com.graduation.presentation.screens.auth.project.adapter.SpinnerAdapter
import com.graduation.presentation.screens.auth.utils.FileUtils
import com.graduation.presentation.screens.auth.utils.imageRefactored
import com.graduation.presentation.screens.main.dev.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint

class AddTaskDetailsFragment :
    BaseFragmentImpl<FragmentAddTaskDetailsBinding>(FragmentAddTaskDetailsBinding::inflate) {

    override val viewModel: AddTaskViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    var type = ""
    private var isImage = false
    private var imageName = ""
    private var fileName = ""
    private var taskId = 0
    private lateinit var selectedPdfFile: File

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        observation()
        setAppBar()
        setupSpinner()
        sharedViewModel.setTextAppBar("Task Details")

        onBackPress {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                navigateTo(R.id.action_addTaskDetailsFragment_to_placeholder)
            }
        }

    }

    private fun observation() {
        viewModel.appliedList.observe(viewLifecycleOwner) { tasks ->
            if (tasks != null) {
                val dataMap = mutableMapOf<String, Any>().apply {
                    put("id", tasks.id)
                }

//                navigateToWithBundle(
//                    R.id.action_addTaskDetailsFragment_to_showTaskDetailsFragment,
//                    dataMap
//                )
            }
        }
    }

    private fun navigation(taskId : Int) {


    }


    private fun setupSpinner() {
        binding.taskTypeSpinner.adapter = SpinnerAdapter(setDummyData())
        binding.attachmentSpinner.adapter = SpinnerAdapter(setAttachmentDummyData())
        binding.timeSpinner.adapter = SpinnerAdapter(setTimeDummyData())
    }

    private fun setDummyData(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(0, "Select type"),
            OnboardingItem(0, "Carpentry"),
            OnboardingItem(0, "Paint"),
            OnboardingItem(0, "Drawing"),
        )
    }

    private fun setAttachmentDummyData(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(0, "Select type"),
            OnboardingItem(0, "Video or Image"),
            OnboardingItem(0, "pdf"),
        )
    }

    private fun setTimeDummyData(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(0, "Select type"),
            OnboardingItem(0, "less than month"),
            OnboardingItem(0, "month"),
            OnboardingItem(0, "more than month"),
        )
    }

    override fun setOnClickListener() {
        binding.publishButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                uploadFile()
                toastMe(requireContext() , "Task Published")
                navigateTo(R.id.action_addTaskDetailsFragment_to_workerTasksFragment)

//                if (taskId != 0) {
//                    val dataMap = mutableMapOf<String, Any>().apply {
//                        put("id", taskId)
//                    }
//                    navigateToWithBundle(
//                        R.id.action_addTaskDetailsFragment_to_showTaskDetailsFragment,
//                        dataMap
//                    )
//                }
            }
        }


        binding.attachmentSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val data = listOf(
                        "noting",
                        "Video or Image",
                        "pdf"
                    )
                    type = data[position]

                    if (type == "Video or Image") {
                        startIntent(Constants.IMAGE_TYPE)
                        isImage = true
                    } else if (type == "pdf")
                        startIntent(Constants.PDF_TYPE)

                    //toastMe(requireContext(), selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when no item is selected
                }
            }


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
                    val m = imageRefactored(requireContext(), result.data!!.data!!)
                    if (m != null) {
                        imageName = m.name
                    }

                } else {
                    val data = result.data!!.data
                    data?.let {

                        selectedPdfFile = uriToFile(data)
//                        val s = FileUtils.getPath(requireContext(), data)
//                        if (s != null) {
//                            selectedPdfFile = File(s)
//                        }
                    }
                }
            }
        }


    private fun uploadFile() {
        val title = "Damaged Door "
        val description = "my door need to be fixed because its wood is damaged"
        val categories = "carpentry"
        val employerId = encryptedSharedPreference.userData.id.toString()
        val date = "2024-07-15T15:20:15-07:00"
        val deadline = "2024-07-20T15:20:15-07:00"
        val price = "5000"
        val address = "Mansoura Mashaya street"

        val requestFile = selectedPdfFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", selectedPdfFile.name, requestFile)

        val titlePart = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val descriptionPart = description.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val categoriesPart = categories.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val employerIdPart = employerId.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val datePart = date.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val deadlinePart = deadline.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val pricePart = price.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val addressPart = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        viewModel.callApplied(
            role = USER_KEY,
            title = titlePart,
            description = descriptionPart,
            categories = categoriesPart,
            employerId = employerIdPart,
            date = datePart,
            deadline = deadlinePart,
            price = pricePart,
            address = addressPart,
            file = filePart
        )
    }

    // Convert Uri to File
    private fun uriToFile(uri: Uri): File {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload", null, requireContext().cacheDir)
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return tempFile

    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

//    data class FormData(
//        val key: String,
//        val value: String?,
//        val type: String,
//        val enabled: Boolean = true,
//    )

}