package com.graduation.presentation.screens.main.user.accept

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.utils.toastMe
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentAcceptTaskBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.dev.home.adapter.HomePostsAdapter
import com.graduation.presentation.screens.main.dev.home.first.DummyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptTaskFragment :
    BaseFragmentImpl<FragmentAcceptTaskBinding>(FragmentAcceptTaskBinding::inflate) {

    override val viewModel: AcceptTaskViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapterItems: HomePostsAdapter
    var taskId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiData()
        setOnClickListener()
        setAppBar()
        observation()
    }

    private fun setUiData() {
        arguments?.apply {
            binding.apply {
                tvCoverLetter.text = getString("coverLetter")
                tvDeadline.text = getString("deadline")
                tvSimilarProject.text = getString("similarProject")
                tvExpectedBudget.text = getString("expectedBudget")
                tvNote.text = getString("note")
                taskId = getInt("id")
            }
        }
    }

    private fun observation() {
        viewModel.apply {
            tasksList.observe(viewLifecycleOwner) { newTask ->
            }
        }
    }


    override fun setOnClickListener() {
        binding.acceptButton.setOnClickListener {
            val applicationId = arguments?.getInt("taskId")
            if (applicationId != null) {
                viewModel.callAcceptTask(
                    role = encryptedSharedPreference.userData.role.toString(),
                    applicationId = applicationId
                )
            }
            toastMe(requireContext() , "Task Accepted")
        }
    }

    override fun setAppBar() {
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

    private fun dummyData(): MutableList<DummyData> {
        return mutableListOf(
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "I wanted Developer to make me android application for my graduationI wanted Developer to make me android application for my graduation I wanted Developer to make me android application for my graduation I wanted Developer to make me android application for my graduation I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
        )
    }

}