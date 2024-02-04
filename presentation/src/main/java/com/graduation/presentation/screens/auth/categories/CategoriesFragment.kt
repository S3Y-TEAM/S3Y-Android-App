package com.graduation.presentation.screens.auth.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentCategoriesBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.categories.adapter.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint

class CategoriesFragment :
    BaseFragmentImpl<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {

    override val viewModel: CategoriesViewModel by viewModels()

    private lateinit var adapterItems: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        setupRV()

    }

    private fun setupRV() {
        adapterItems = CategoriesAdapter(kind = true)
        adapterItems.differ.submitList(setUpFriendsArrayList())
        binding.categoriesRv.apply {
            adapter = adapterItems
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
        }
    }

    private fun setUpFriendsArrayList(): List<String> {
        return listOf(
            "Android",
            "Flutter",
            "IOS",
            "UI/UX",
            "Back-end",
            "Front-end",
            "Security",
            "Computer Science",
            "AI",
            "Game Development",
            "Data Science",
            "Embedded System"
        )
    }

    override fun setOnClickListener() {

        binding.apply {
            categoriesButton.setOnClickListener {
                lifecycleScope.launch {
                    categoriesButton.loadingDrawable.strokeWidth =
                        categoriesButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)

                    navigateTo(R.id.action_categoriesFragment_to_experienceFragment)
                }
            }

            categoriesAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.categoriesAppBar.appBarTitle.text = resources.getText(R.string.categories)
    }

    override fun onLoadingStart() {
        binding.categoriesButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.categoriesButton.complete(true)
    }

    override fun onCancel() {
        binding.categoriesButton.cancel()
    }
}