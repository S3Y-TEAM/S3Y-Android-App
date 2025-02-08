package com.graduation.presentation.screens.main.dev.applied

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.domain.models.main.dev.applied.Application
import com.graduation.domain.models.main.dev.tasks.Task
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentAppliedBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.dev.applied.adapter.AppliedAdapter
import com.graduation.presentation.screens.main.dev.home.first.DummyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AppliedFragment : BaseFragmentImpl<FragmentAppliedBinding>(FragmentAppliedBinding::inflate) {

    override val viewModel: AppliedViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("Applied Jobs")
        observation()
        callApplied()

    }

    private fun callApplied() {
        viewModel.callApplied(
            role = encryptedSharedPreference.userData.role.toString(),
            employeeId = encryptedSharedPreference.userData.id!!.toInt()
        )
    }

    private fun observation() {
        viewModel.appliedList.observe(viewLifecycleOwner) { applied ->
            if (applied != null)
                setupRV(listOfApplied = applied)

        }
        viewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null)
                encryptedSharedPreference.token = token

        }
        viewModel.appliedError.observe(viewLifecycleOwner) { error ->
        }
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

    private fun setupRV(listOfApplied: List<Application>?) {
        val adapterItems = AppliedAdapter()
        adapterItems.differ.submitList(listOfApplied)
        binding.appliedRv.apply {
            adapter = adapterItems
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
        }
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