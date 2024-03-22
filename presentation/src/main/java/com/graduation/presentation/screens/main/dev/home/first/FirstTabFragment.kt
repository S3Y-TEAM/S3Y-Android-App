package com.graduation.presentation.screens.main.dev.home.first

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.navigateToWithBundle
import com.graduation.domain.models.main.user.home.Task
import com.graduation.presentation.Constants.SKILLED_KEY
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentFirstTabBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.dev.home.HomeViewModel
import com.graduation.presentation.screens.main.dev.home.adapter.HomePostsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstTabFragment :
    BaseFragmentImpl<FragmentFirstTabBinding>(FragmentFirstTabBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapterItems: HomePostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callData()
        setOnClickListener()
        setAppBar()
//        setupRV()
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


    private fun callData() {
        viewModel.callHome(role = SKILLED_KEY, category = "carpentry")
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
        adapterItems = HomePostsAdapter { item ->
            val dataMap = mutableMapOf<String, Any>().apply {
                put("title", item.title)
                put("lname", item.employer.lname)
                put("fname", item.employer.fname)
                put("descr", item.descr)
                put("address", item.address)
                put("price", item.price)
                put("city", item.city)
                put("note", item.note)
                put("deadline", item.deadline)
                put("id", item.id)
            }
            navigateToWithBundle(R.id.action_homeFragment_to_showTaskDetailsFragment2, dataMap)


            // Handle the click event here
        }
        adapterItems.differ.submitList(data)
        binding.firstTabRv.apply {
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