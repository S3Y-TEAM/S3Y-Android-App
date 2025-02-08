//package com.graduation.presentation.screens.main.notification.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.graduation.presentation.databinding.NotificationItemBinding
//import com.graduation.presentation.screens.main.home.first.DummyData
//
//class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.AllNotificationAdapter>() {
//
//    inner class AllNotificationAdapter(val binding: NotificationItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//    }
//
//
//    private val differCallback = object : DiffUtil.ItemCallback<DummyData>() {
//        override fun areItemsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
//            return oldItem.name == newItem.name
//        }
//
//        override fun areContentsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNotificationAdapter {
//        val binding =
//            NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return AllNotificationAdapter(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//    override fun onBindViewHolder(holder: AllNotificationAdapter, position: Int) {
//        val item = differ.currentList[position]
//        holder.binding.apply {
//            notificationDescription.text = item.description
//            notificationClientImage.setImageResource(item.image)
//        }
//    }
//}
package com.graduation.presentation.screens.main.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.presentation.databinding.NotificationItemBinding
import com.graduation.presentation.screens.main.dev.home.first.DummyData

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.AllNotificationAdapter>() {

    inner class AllNotificationAdapter(val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<DummyData>() {
        override fun areItemsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNotificationAdapter {
        val binding =
            NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllNotificationAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllNotificationAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            if (item.isPinned)
                pinIcon.visibility = View.VISIBLE
            notificationDescription.text = item.description
            notificationClientImage.setImageResource(item.image)
        }
    }

    var recentlyDeletedItem: DummyData? = null
        private set
    var recentlyDeletedItemPosition: Int = -1
        private set

    fun deleteItem(position: Int) {
        recentlyDeletedItem = differ.currentList[position]
        recentlyDeletedItemPosition = position
        val newList = differ.currentList.toMutableList()
        newList.removeAt(position)
        differ.submitList(newList)
    }

    fun restoreItem() {
        recentlyDeletedItem?.let { item ->
            val newList = differ.currentList.toMutableList()
            if (recentlyDeletedItemPosition >= 0 && recentlyDeletedItemPosition <= newList.size) {
                newList.add(recentlyDeletedItemPosition, item)
                differ.submitList(newList)
                recentlyDeletedItem = null
                recentlyDeletedItemPosition = -1
            }
        }
    }

    fun pinItem(position: Int) {
        val newList = differ.currentList.toMutableList()
        val item = newList.removeAt(position)
        item.isPinned = true
        newList.add(0, item)
        differ.submitList(newList)
    }
}


