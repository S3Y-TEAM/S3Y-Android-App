package com.graduation.presentation.screens.auth.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.presentation.databinding.OnboardingItemBinding
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem


class OnboardingAdapter : RecyclerView.Adapter<OnboardingAdapter.AllCategoriesAdapter>() {

    inner class AllCategoriesAdapter(val binding: OnboardingItemBinding /*val viewPagerId :  ViewPager2*/) :
        RecyclerView.ViewHolder(binding.root) {}

    private val differCallback = object : DiffUtil.ItemCallback<OnboardingItem>() {
        override fun areItemsTheSame(oldItem: OnboardingItem, newItem: OnboardingItem): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: OnboardingItem, newItem: OnboardingItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoriesAdapter {
        val binding =
            OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCategoriesAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllCategoriesAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            onboardingDescription.text = item.description.toString()
            onboardingImage.setImageResource(item.image)
        }
    }
}