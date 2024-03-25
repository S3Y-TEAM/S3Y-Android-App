package com.graduation.presentation.screens.main.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.domain.models.main.dev.tasks.Data
import com.graduation.domain.models.main.dev.tasks.Task
import com.graduation.presentation.R
import com.graduation.presentation.databinding.TasksItemBinding
import com.graduation.presentation.screens.main.dev.home.first.DummyData

class TasksAdapter(
    private val onItemClick: ((Task) -> Unit)? = null,
) : RecyclerView.Adapter<TasksAdapter.AllTasksAdapter>() {

    inner class AllTasksAdapter(val binding: TasksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    private val differCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTasksAdapter {
        val binding =
            TasksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllTasksAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllTasksAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            taskClientImage.setImageResource(R.drawable.ic_person)
            taskTitle.text = item.title
            taskMoney.text = item.price
            taskCategory.text = item.category.name
            taskState.text = item.status

            root.setOnClickListener {
                onItemClick?.invoke(item)
            }

        }
    }
}