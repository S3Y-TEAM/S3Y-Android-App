package com.graduation.presentation.screens.auth.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.graduation.presentation.databinding.DropdownStyleBinding
import com.graduation.presentation.databinding.SpinnerStyleBinding
import com.graduation.presentation.screens.auth.onboarding.model.OnboardingItem


class SpinnerAdapter(private val objects: List<OnboardingItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(position: Int): Any {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position) as OnboardingItem
        var v = convertView
        if (v == null) {
            val vi = SpinnerStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            v = vi.root
            vi.dropdownLayout.apply {
                typeText.text = item.description
                typeIcon.setImageResource(item.image)
            }
        }
        return v
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            DropdownStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val item = objects[position]
        binding.typeText.text = item.description
        binding.typeIcon.setImageResource(item.image)
        return binding.root
    }
}
