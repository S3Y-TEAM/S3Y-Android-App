package com.graduation.presentation.screens.auth.experience

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentExperienceBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.categories.adapter.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint

class ExperienceFragment :
    BaseFragmentImpl<FragmentExperienceBinding>(FragmentExperienceBinding::inflate) {

    override val viewModel: ExperienceViewModel by viewModels()

    private lateinit var adapterItems: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        setupRV()

    }

    private fun setupRV() {
        adapterItems = CategoriesAdapter(kind = false)
        adapterItems.differ.submitList(setUpFriendsArrayList().toList())
        binding.experienceRv.apply {
            adapter = adapterItems
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
        }
    }

    private fun setUpFriendsArrayList(): List<String> {
        return listOf(
            "less than 1 year",
            "From 1 year to 3 year",
            "From 3 year to 5 year",
            "From 5 year to 8 year",
            "More than 8 year"
        )
    }

    override fun setOnClickListener() {

        binding.apply {
            experienceButton.setOnClickListener {
                lifecycleScope.launch {
                    experienceButton.loadingDrawable.strokeWidth =
                        experienceButton.textSize * 0.14f;
                    onLoadingStart()
                    delay(1600)
                    onComplete(true)
                    delay(500)
                    navigateTo(R.id.action_experienceFragment_to_projectFragment)
                }
            }

            experienceAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            experienceAppBar.appBarSkipBtn.setOnClickListener {
                navigateTo(R.id.action_experienceFragment_to_projectFragment)
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.experienceAppBar.apply {
            appBarTitle.text = resources.getText(R.string.experience)
            appBarSkipBtn.visibility = View.VISIBLE
        }
    }

    override fun onLoadingStart() {
        binding.experienceButton.start()
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.experienceButton.complete(true)
    }

    override fun onCancel() {
        binding.experienceButton.cancel()
    }

}