package com.graduation.presentation.screens.main.dev.apply

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.domain.models.main.dev.apply.ApplyRequest
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentApplyBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyFragment :
    BaseFragmentImpl<FragmentApplyBinding>(FragmentApplyBinding::inflate) {

    override val viewModel: ApplyViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("Apply Task")

        onBackPress {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                navigateTo(R.id.action_applyFragment_to_showTaskDetailsFragment2)
            }
        }


    }

    override fun setOnClickListener() {
        binding.apply {
            publishButton.setOnClickListener {
                val role = encryptedSharedPreference.userData.role.toString()
                val applyRequest = ApplyRequest(
                    coverLetter = etCoverLetter.text.toString(),
                    similarProject = etSimilarProject.text.toString(),
                    expectedBudget = etExpectedBudget.text.toString().toInt() ?: 5000,
                    deadline = /*etDeadline.text.toString()*/  "2024-08-01T22:20:15.000Z",
                    note = noteEdittext.text.toString()
                )
                val taskId = arguments?.getInt("id")
                if (taskId != null) {
                    viewModel.callApplyTask(
                        role = role, applyRequest = applyRequest, taskId = taskId
                    )
                }
            }
        }
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
}