package com.graduation.presentation.screens.auth.experience

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.domain.models.auth.categories.CategoriesResponseItem
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
    override val sharedViewModel: SharedViewModel by viewModels()

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

    private fun setUpFriendsArrayList(): List<CategoriesResponseItem> {
        return listOf(
            CategoriesResponseItem(id = 0, name = "less than 1 year", parent = "exp"),
            CategoriesResponseItem(id = 1, name = "From 1 year to 3 year", parent = "exp"),
            CategoriesResponseItem(id = 2, name = "From 3 year to 5 year", parent = "exp"),
            CategoriesResponseItem(id = 3, name = "From 5 year to 8 year", parent = "exp"),
            CategoriesResponseItem(id = 4, name = "More than 8 year", parent = "exp"),
        )
    }

    override fun setOnClickListener() {

        binding.apply {
            experienceButton.setOnClickListener {
                lifecycleScope.launch {
                    onLoadingStart()
                    if (adapterItems.selectedExperience == -1) {
                        Toast.makeText(
                            requireContext(),
                            "Select maximum one Experience",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        onCancel()
                    }else {
                        sharedViewModel.setExperience(adapterItems.experience)
                        onComplete(true)
                        delay(500)
                        navigateTo(R.id.action_experienceFragment_to_projectFragment)
                    }
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
        binding.apply {
            experienceButton.loadingDrawable.strokeWidth =
                experienceButton.textSize * 0.14f;
            experienceButton.start()
        }
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.experienceButton.complete(true)
    }

    override fun onCancel() {
        binding.experienceButton.cancel()
    }

}