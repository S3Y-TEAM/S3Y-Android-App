package com.graduation.presentation.screens.main.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.presentation.databinding.ChatItemBinding
import com.graduation.presentation.screens.main.dev.home.first.DummyData

class AllChatsAdapter : RecyclerView.Adapter<AllChatsAdapter.AllChatsItemsAdapter>() {

    inner class AllChatsItemsAdapter(val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    private val differCallback = object : DiffUtil.ItemCallback<DummyData>() {
        override fun areItemsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllChatsItemsAdapter {
        val binding =
            ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllChatsItemsAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllChatsItemsAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            chatImage.setImageResource(item.image)
            chatMessage.text = item.description
            chatName.text = item.name

        }
    }
}