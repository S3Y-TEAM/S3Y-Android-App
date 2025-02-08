package com.graduation.presentation.screens.main.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateToWithBundle
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.domain.models.main.dev.tasks.Task
import com.graduation.presentation.Constants.USER_KEY
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentTasksBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.tasks.adapter.TasksAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class TasksFragment : BaseFragmentImpl<FragmentTasksBinding>(FragmentTasksBinding::inflate) {

    override val viewModel: TasksViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        observation()
        checkRole()
//        callDevTasks()
        sharedViewModel.setTextAppBar("Tasks")

    }

    private fun checkRole() {
        if (encryptedSharedPreference.userData.role.toString() == USER_KEY)
            callUserTasks()
        else
            callDevTasks()
    }

    private fun observation() {
        viewModel.tasksList.observe(viewLifecycleOwner) { tasks ->
            if (tasks != null)
                setupRV(listOfTasks = tasks)

        }
        viewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null)
                encryptedSharedPreference.token = token

        }
        viewModel.tasksError.observe(viewLifecycleOwner) { error ->

            //handle error
        }
    }

    private fun setupRV(listOfTasks: List<Task>?) {
        val adapterItems = TasksAdapter { item ->
            val dataMap = mutableMapOf<String, Any>().apply {
                put("idFromTask", item.id)
            }
            navigateToWithBundle(R.id.action_workerTasksFragment_to_showTaskDetailsFragment, dataMap)


            // Handle the click event here
        }
        adapterItems.differ.submitList(listOfTasks)
        binding.tasksRv.apply {
            adapter = adapterItems
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
        }
    }

    private fun callDevTasks() {
        viewModel.callDevTasks(
            role = encryptedSharedPreference.userData.role.toString(),
            employeeId = encryptedSharedPreference.userData.id!!.toInt()
        )
    }

    private fun callUserTasks() {
        viewModel.callUserTasks(
            userId = encryptedSharedPreference.userData.id!!.toInt()
        )
    }

    override fun setOnClickListener() {
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