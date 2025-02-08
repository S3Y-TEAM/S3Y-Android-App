package com.graduation.presentation.screens.main.dev.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentHomeBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.dev.home.adapter.TapViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragmentImpl<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        setTabViewer()
        sharedViewModel.setTextAppBar("S3Y")
    }


    private fun setTabViewer() {
        val adapter = TapViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null)
                    binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

//    private var drawerOpener: DrawerOpener? = null
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is DrawerOpener) {
//            drawerOpener = context
//        } else {
//            throw RuntimeException("$context must implement DrawerOpener")
//        }
//    }

    override fun setOnClickListener() {

//        binding.homeAppBar.appBarBackArrow.setOnClickListener {
//            drawerOpener?.openDrawer()
//        }

    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
//        binding.homeAppBar.appBarTitle.text = resources.getText(R.string.app_name)
//        binding.homeAppBar.appBarBackArrow.setImageResource(R.drawable.app_logo)
//        binding.homeAppBar.searchIcon.visibility = View.VISIBLE
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

    override fun onPause() {
        super.onPause()
        setTabViewer()
    }
}

//interface DrawerOpener {
//    fun openDrawer()
//}