package com.graduation.presentation.screens.main.user.applicants

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.navigateToWithBundle
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.domain.models.main.user.create.task.details.ApplicantsItem
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentApplicantsBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.dev.home.first.DummyData
import com.graduation.presentation.screens.main.drawer.rate.RateUsViewModel
import com.graduation.presentation.screens.main.user.show.adapter.ApplicantsAdapter

class ApplicantsFragment :
    BaseFragmentImpl<FragmentApplicantsBinding>(FragmentApplicantsBinding::inflate) {

    override val viewModel: RateUsViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        setupRV()
        sharedViewModel.setTextAppBar("Applicants")

        onBackPress {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                navigateTo(R.id.action_applicantsFragment_to_showTaskDetailsFragment)
            }
        }

    }


    private fun setupRV() {
        val applicantsAdapter = ApplicantsAdapter { item ->
            val dataMap = mutableMapOf<String, Any>().apply {
                put("taskId", item.taskId)
                put("id", item.id)
                put("coverLetter", item.coverLetter)
                put("similarProject", item.similarProject)
                put("expectedBudget", item.expectedBudget)
                put("deadline", item.deadline)
                put("note", item.note)
            }
            navigateToWithBundle(R.id.action_applicantsFragment_to_acceptTaskFragment, dataMap)


            // Handle the click event here
        }
        applicantsAdapter.differ.submitList(dummyData())
        binding.applicantsRv.apply {
            adapter = applicantsAdapter
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
        }
    }


    private fun dummyData(): MutableList<ApplicantsItem> {
        return mutableListOf(
            ApplicantsItem(
                accepted = false,
                coverLetter = "Android Application",
                deadline = "I wanted Developer to make me android application for my graduation ",
                employeeId = 101,
                expectedBudget = 5000,
                id = 101,
                note = "101",
                similarProject = "none",
                taskId = 357
            )
        )
    }


    override fun setOnClickListener() {
    }

    override fun setAppBar() {
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

}