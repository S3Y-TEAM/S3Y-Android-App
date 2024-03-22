package com.graduation.presentation.screens.main.dev.home.adapter

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.graduation.presentation.screens.main.dev.home.first.FirstTabFragment
import com.graduation.presentation.screens.main.dev.home.second.SecondTabFragment

class TapViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): androidx.fragment.app.Fragment {
        return when (position) {
            0 -> FirstTabFragment()
            else -> SecondTabFragment()
        }
    }
}