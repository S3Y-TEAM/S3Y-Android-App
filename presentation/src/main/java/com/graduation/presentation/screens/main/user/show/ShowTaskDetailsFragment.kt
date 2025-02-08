package com.graduation.presentation.screens.main.user.show

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.navigateToWithBundle
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.domain.models.main.user.create.task.details.Applicants
import com.graduation.domain.models.main.user.create.task.details.Task
import com.graduation.presentation.Constants.DEVELOPER_KEY
import com.graduation.presentation.Constants.SKILLED_KEY
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentShowTaskDetailsBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowTaskDetailsFragment :
    BaseFragmentImpl<FragmentShowTaskDetailsBinding>(FragmentShowTaskDetailsBinding::inflate) {

    override val viewModel: ShowTaskDetailsViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    private var myData: Task? = null
    var role = ""
    private var applicants = Applicants()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        role = encryptedSharedPreference.userData.role.toString()

        setUiData()
        observation()
        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("Task Data")

        onBackPress {
            if (role == DEVELOPER_KEY ||
                role == SKILLED_KEY
            ) {
                requireActivity().onBackPressedDispatcher.addCallback(this) {
                    navigateTo(R.id.action_showTaskDetailsFragment2_to_homeFragment)
                }
            } else {
                navigateTo(R.id.action_showTaskDetailsFragment_to_addTaskDetailsFragment)
            }
        }

    }

    private fun observation() {
        viewModel.taskData.observe(viewLifecycleOwner) { tasks ->
            if (tasks != null) {
                setCallUiData(tasks)
                setupArrayList(tasks.applicants)
            }

        }

        viewModel.tasksList.observe(viewLifecycleOwner) { applicants ->
            if (applicants != null)
                setupArrayList(applicants)
        }

    }

    private fun setCallUiData(tasks: Task) {
        tasks.apply {
            binding.apply {
                tvTaskTitle.text = title
                tvClientName.text = employer.fname + employer.lname
                tvTaskDescription.text = descr
                tvTaskLocation.text = city + address
                tvTaskType.text = "carpentry"
                tvTaskBudget.text = price.toString()
                tvTaskNote.text = note
                tvTime.text = deadline
            }
        }
    }

    private fun setupArrayList(listOfTasks: Applicants) {
        applicants = listOfTasks
    }

    private fun setUiData() {
        if (role == DEVELOPER_KEY ||
            role == SKILLED_KEY
        ) {
            setButtonText("Apply")
        } else
            setButtonText("Show Applicants")

        var taskId = arguments?.getInt("idFromTask")
        if (taskId != null)
            viewModel.callTaskDetails(role = role, taskId)
        else {
            taskId = arguments?.getInt("id")
            if (taskId != null)
                viewModel.callTaskDetails(role = role, taskId)

        }

//        arguments?.apply {
//            binding.apply {
//                tvTaskTitle.text = getString("title")
//                tvClientName.text = getString("fname") + getString("lname")
//                tvTaskDescription.text = getString("descr")
//                tvTaskLocation.text = getString("city") + getString("address")
//                tvTaskType.text = "carpentry"
//                tvTaskBudget.text = getString("price")
//                tvTaskNote.text = getString("note")
//                tvTime.text = getString("deadline")
//            }
//        }


    }

    private fun setButtonText(text: String) {
        binding.showApplicantsButton.text = text
    }

    override fun setOnClickListener() {
        binding.showApplicantsButton.setOnClickListener {
            if (role == DEVELOPER_KEY ||
                role == SKILLED_KEY
            ) {
                val taskId = arguments?.getInt("id")
                val dataMap = mutableMapOf<String, Any>().apply {
                    if (taskId != null) {
                        put("id", taskId)
                    }
                }
                //navigation apply
                navigateToWithBundle(R.id.action_showTaskDetailsFragment2_to_applyFragment, dataMap)
            } else {

                val dataMap = mutableMapOf<String, Any>().apply {

                    if (applicants.size > 0) {

                        put("id", applicants[0].id)
                        put("coverLetter", applicants[0].coverLetter)
                        put("similarProject", applicants[0].similarProject)
                        put("expectedBudget", applicants[0].expectedBudget)
                        put("deadline", applicants[0].deadline)
                        put("note", applicants[0].note)
                        put("taskId", applicants[0].taskId)
                    }
                }


                navigateToWithBundle(
                    R.id.action_showTaskDetailsFragment_to_applicantsFragment,
                    dataMap
                )
            }
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

}