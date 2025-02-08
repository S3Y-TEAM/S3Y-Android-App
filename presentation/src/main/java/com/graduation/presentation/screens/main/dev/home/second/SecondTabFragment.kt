package com.graduation.presentation.screens.main.dev.home.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.domain.models.main.user.home.Task
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentSecondTabBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.dev.home.HomeViewModel
import com.graduation.presentation.screens.main.dev.home.adapter.HomePostsAdapter
import com.graduation.presentation.screens.main.dev.home.first.DummyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondTabFragment :
    BaseFragmentImpl<FragmentSecondTabBinding>(FragmentSecondTabBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapterItems: HomePostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        observation()
    }

    private fun observation() {
        viewModel.apply {
            appliedList.observe(viewLifecycleOwner) { newTask ->
                if (newTask != null)
                    setupRV(newTask)
            }
        }
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

    private fun setupRV(data: List<Task>) {
//        adapterItems = HomePostsAdapter()
        adapterItems = HomePostsAdapter { item ->
            // Handle the click event here
        }
        adapterItems.differ.submitList(data)
        binding.secondTabRv.apply {
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