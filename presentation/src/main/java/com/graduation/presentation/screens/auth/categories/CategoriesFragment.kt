package com.graduation.presentation.screens.auth.categories

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.domain.models.auth.Auth.UserNameRequest
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponseItem
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
    override val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var adapterItems: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        observation()
        callCategories()

    }

    private fun observation() {
        viewModel.categoriesList.observe(viewLifecycleOwner) { categoriesList ->
            if (categoriesList != null)
                setupRV(categoriesList = categoriesList)

        }
        viewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null)
                encryptedSharedPreference.token = token

        }
    }

    private fun callCategories() {
        viewModel.callCategories(
            role = sharedViewModel.role.value.toString(),
            categoriesRequest = UserNameRequest(userName = sharedViewModel.username.value.toString())
        )
    }

    private fun setupRV(categoriesList: List<CategoriesResponseItem>) {
        adapterItems = CategoriesAdapter(kind = true)
        adapterItems.differ.submitList(categoriesList)
        binding.categoriesRv.apply {
            adapter = adapterItems
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
        }
    }

    override fun setOnClickListener() {

        binding.apply {
            categoriesButton.setOnClickListener {
                lifecycleScope.launch {
                    onLoadingStart()
                    if (adapterItems.chooseCategory.size == 0) {
                        onCancel()
                        Toast.makeText(
                            requireContext(),
                            "Please, Choose minimum one category ",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        sharedViewModel.setSavedCategories(adapterItems.chooseCategory)
                        onComplete(true)
                        delay(500)
                        navigateTo(R.id.action_categoriesFragment_to_experienceFragment)
                    }
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
        binding.apply {
            categoriesButton.loadingDrawable.strokeWidth =
                categoriesButton.textSize * 0.14f;
            binding.categoriesButton.start()
        }
    }

    override fun onComplete(isSuccess: Boolean) {
        binding.categoriesButton.complete(true)
    }

    override fun onCancel() {
        binding.categoriesButton.cancel()
    }
}