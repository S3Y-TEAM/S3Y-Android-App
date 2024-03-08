package com.graduation.presentation.screens.auth.project

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
import com.graduation.core.utils.hideKeypad
import com.graduation.core.utils.toastMe
import com.graduation.domain.models.auth.signup.Category
import com.graduation.presentation.Constants.IMAGE_TYPE
import com.graduation.presentation.Constants.PDF_TYPE
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentProjectBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem
import com.graduation.presentation.screens.auth.project.adapter.ProjectAdapter
import com.graduation.presentation.screens.auth.project.adapter.SpinnerAdapter
import com.graduation.presentation.screens.auth.utils.FileUtils
import com.graduation.presentation.screens.auth.utils.imageRefactored
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ProjectFragment : BaseFragmentImpl<FragmentProjectBinding>(FragmentProjectBinding::inflate) {

    override val viewModel: ProjectViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var adapterItems: ProjectAdapter
    private var items: MutableList<String> = mutableListOf()
    private lateinit var selectedPdfFile: File
    private var isImage = false
    private var imageName = ""
    private var fileName = ""
    private val category = arrayListOf(OnboardingItem(0, "Select type"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        setupRV()
    }

    private fun setupSpinner() {
        binding.projectItem.spinner.adapter = SpinnerAdapter(setDummyData())
    }

    private fun setDummyData(): List<OnboardingItem> {
        for (i in sharedViewModel.chosenCategories.value!!) {
            category.add(OnboardingItem(R.drawable.ic_job, i))
        }
        return category
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
                requireActivity().hideKeypad()
                if (binding.projectItem.projectNameEdittext.text.isNullOrBlank()) {
                    toastMe(requireContext(), "Please Enter Project Title")
                } else {
                    items.add(binding.projectItem.projectNameEdittext.text.toString())
                    binding.projectRv.adapter!!.notifyItemInserted(items.size - 1)

                    projectItem.root.visibility = View.GONE
                    binding.projectItem.projectNameEdittext.text = null
                    binding.projectItem.descriptionEdittext.text = null
                }
            }
            projectItem.projectFile.setOnClickListener {
                showBottomSheet()
            }
            uploadProjectBtn.setOnClickListener {
                if (projectItem.root.visibility == View.VISIBLE)
                    Toast.makeText(requireActivity(), "Please complete data", Toast.LENGTH_SHORT)
                        .show()
                else {
                    setupSpinner()
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
            isImage = true
            dialog.dismiss()
        }
        val btnPdf: LinearLayout = view.findViewById(R.id.pdf_type)
        btnPdf.setOnClickListener {
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
                if (isImage) {
                    binding.blurView.visibility = View.GONE
                    val m = imageRefactored(requireContext(), result.data!!.data!!)
                    if (m != null) {
                        imageName = m.name
                        binding.projectItem.projectFile.text = m.name
                    }

                } else {
                    val data = result.data!!.data
                    binding.blurView.visibility = View.GONE
                    data?.let {
                        val s = FileUtils.getPath(requireContext(), data)
                        if (s != null) {
                            selectedPdfFile = File(s)
                            binding.projectItem.projectFile.text = selectedPdfFile.name
                        }
                    }
                }
            } else
                binding.blurView.visibility = View.GONE
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