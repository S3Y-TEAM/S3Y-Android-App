package com.graduation.presentation.screens.auth.experience

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentCategoriesBinding
import com.graduation.presentation.databinding.FragmentExperienceBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.categories.CategoriesViewModel
import com.graduation.presentation.screens.auth.categories.adapter.CategoriesAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExperienceFragment :
    BaseFragmentImpl<FragmentExperienceBinding>(FragmentExperienceBinding::inflate) {

    override val viewModel: CategoriesViewModel by viewModels()

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
            val flexboxLayoutManager =
                LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
            layoutManager = flexboxLayoutManager
        }
    }

    private fun setUpFriendsArrayList(): ArrayList<String> {
        val dummyData = ArrayList<String>()
        dummyData.add(0, "less than 1 year")
        dummyData.add(1, "From 1 year to 3 year")
        dummyData.add(2, "From 3 year to 5 year")
        dummyData.add(3, "From 5 year to 8 year")
        dummyData.add(4, "More than 8 year")

        return dummyData
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
                }
            }

            experienceAppBar.appBarBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
        binding.experienceAppBar.appBarTitle.text = resources.getText(R.string.experience)
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