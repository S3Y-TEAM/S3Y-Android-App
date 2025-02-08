package com.graduation.presentation.screens.main.dev.applied.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.domain.models.main.dev.applied.Application
import com.graduation.presentation.R
import com.graduation.presentation.databinding.AppliedItemBinding
import com.graduation.presentation.screens.main.dev.home.first.DummyData

class AppliedAdapter : RecyclerView.Adapter<AppliedAdapter.AllAppliedAdapter>() {

    inner class AllAppliedAdapter(val binding: AppliedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    private val differCallback = object : DiffUtil.ItemCallback<Application>() {
        override fun areItemsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAppliedAdapter {
        val binding =
            AppliedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllAppliedAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllAppliedAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            appliedTitle.text = item.title
            appliedClientImage.setImageResource(R.drawable.ic_person)
            appliedClientName.text = item.employerId.toString()

        }
    }
}