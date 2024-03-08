package com.graduation.presentation.screens.auth.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.core.utils.toastMe
import com.graduation.presentation.databinding.ProjectItemBinding

class ProjectAdapter : RecyclerView.Adapter<ProjectAdapter.AllProjectsAdapter>() {

    inner class AllProjectsAdapter(val binding: ProjectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProjectsAdapter {
        val binding =
            ProjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllProjectsAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllProjectsAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.root.apply {

            holder.binding.apply {
                projectTitle.text = item.toString()
                editProject.setOnClickListener {
                    toastMe(context = context , "Coming Soon")
                }
            }

        }
    }

}