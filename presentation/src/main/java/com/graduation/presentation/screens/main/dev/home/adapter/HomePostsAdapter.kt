package com.graduation.presentation.screens.main.dev.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.domain.models.main.user.home.Task
import com.graduation.presentation.databinding.HomeTaskItemBinding
import com.squareup.picasso.Picasso

class HomePostsAdapter(
    private val onItemClick: ((Task) -> Unit)? = null,
) : RecyclerView.Adapter<HomePostsAdapter.AllPostsAdapter>() {

    inner class AllPostsAdapter(val binding: HomeTaskItemBinding) :
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPostsAdapter {
        val binding =
            HomeTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllPostsAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllPostsAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            homeTaskTitle.text = item.title
            clientName.text = item.employer.fname + item.employer.lname
            postDescription.text = item.descr

            val imageUrl =
                "https://drive.google.com/uc?id=16O2wi_3ZCXMdfh6n85AYkO4LJaS9Q6qD&export=view"

//            Glide.with(clientImage)
//                .load(imageUrl)
//                .into(clientImage)

            Picasso.get()
                .load(imageUrl)
                .into(clientImage)

            root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}