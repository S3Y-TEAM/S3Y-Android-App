package com.graduation.presentation.screens.auth.project.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.graduation.presentation.R
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
        var v = convertView
        val holder: ViewHolder
        val item = getItem(position) as OnboardingItem

        if (v == null) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.spinner_style, parent, false)
            holder = ViewHolder(v)
            v.tag = holder
        } else {
            holder = v.tag as ViewHolder
        }

        holder.typeText.text = item.description
        holder.typeIcon.setImageResource(item.image)

        return v!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            DropdownStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val item = objects[position]
        binding.typeText.text = item.description
        binding.typeIcon.setImageResource(item.image)
        return binding.root
    }

    private class ViewHolder(view: View) {
        val typeText: TextView = view.findViewById(R.id.type_text)
        val typeIcon: ImageView = view.findViewById(R.id.type_icon)
    }
}
