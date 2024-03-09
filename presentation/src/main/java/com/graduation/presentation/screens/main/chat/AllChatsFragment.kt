package com.graduation.presentation.screens.main.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentAllChatsBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.chat.adapter.AllChatsAdapter
import com.graduation.presentation.screens.main.dev.home.HomeViewModel
import com.graduation.presentation.screens.main.dev.home.first.DummyData

class AllChatsFragment : BaseFragmentImpl<FragmentAllChatsBinding>(FragmentAllChatsBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("Chats")
        setupRV()


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

    private fun setupRV() {
        val adapterItems = AllChatsAdapter()
        adapterItems.differ.submitList(dummyData())
        binding.chatsRv.apply {
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