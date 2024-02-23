package com.graduation.presentation.screens.auth.onboarding

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentOnboardingBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.auth.onboarding.adapter.OnboardingAdapter
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment :
    BaseFragmentImpl<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    override val viewModel: OnboardingViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var adapterItems: OnboardingAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        setAppBar()
        setOnboardingRV()
        observeViewPager()

    }

    private fun observeViewPager() {
        binding.apply {
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (viewPager2.currentItem == 0) {
                        true -> previous.visibility = View.GONE
                        false -> previous.visibility = View.VISIBLE
                    }
                    when (viewPager2.currentItem + 1 == adapterItems.itemCount) {
                        true -> next.text = resources.getText(R.string.start)
                        false -> next.text = resources.getText(R.string.next)
                    }
                }
            })
        }
    }

    override fun setOnClickListener() {
        binding.apply {
            next.setOnClickListener {
                if (viewPager2.currentItem + 1 < adapterItems.itemCount)
                    viewPager2.currentItem = viewPager2.currentItem + 1
                else {
                    appOpened()
                }
            }
            previous.setOnClickListener {
                viewPager2.currentItem = viewPager2.currentItem - 1
            }
            skipBtn.setOnClickListener {
                sharedPreferenceHelper.openedTheAppBefore = true
                navigateTo(R.id.action_onboardingFragment_to_positionFragment)
            }
            onBackPress {
                requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
                    return@addCallback requireActivity().finish()
                }
            }
        }
    }

    private fun appOpened() {
        sharedPreferenceHelper.openedTheAppBefore = true
        navigateTo(R.id.action_onboardingFragment_to_positionFragment)
    }

    private fun setOnboardingRV() {
        adapterItems = OnboardingAdapter()
        adapterItems.differ.submitList(setOnboardingData().toList())
        binding.viewPager2.apply {
            adapter = adapterItems
        }
    }

    private fun setOnboardingData(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(
                image = R.drawable.onboarding_1,
                description = resources.getText(R.string.onboarding_one).toString()
            ),
            OnboardingItem(
                image = R.drawable.onboarding_2,
                description = resources.getText(R.string.onboarding_two).toString()
            ),
            OnboardingItem(
                image = R.drawable.onboarding_3,
                description = resources.getText(R.string.onboarding_three).toString()
            ),
            OnboardingItem(
                image = R.drawable.onboarding_4,
                description = resources.getText(R.string.onboarding_four).toString()
            )
        )
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.background, isContentLight = false, isTransparent = false)
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

}