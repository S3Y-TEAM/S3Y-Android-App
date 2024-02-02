package com.graduation.presentation.screens.auth.categories.adapter

import android.app.ActionBar.LayoutParams
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.core.extensions.getResourceColor
import com.graduation.presentation.R
import com.graduation.presentation.databinding.CategoriesItemBinding

class CategoriesAdapter(var kind : Boolean) : RecyclerView.Adapter<CategoriesAdapter.AllCategoriesAdapter>() {

    inner class AllCategoriesAdapter(val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoriesAdapter {
        val binding =
            CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCategoriesAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllCategoriesAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.root.apply {

            when (kind){
                true -> holder.binding.root.layoutParams.width = LayoutParams.WRAP_CONTENT
                false -> holder.binding.root.layoutParams.width = LayoutParams.MATCH_PARENT
            }

            holder.binding.apply {
                btnItem.text = item.toString()
            }
            setOnClickListener {
                holder.binding.apply {
                    btnItem.setBackgroundColor(
                        btnItem.context.getResourceColor(
                            R.color.main_color
                        )
                    )
                    btnItem.setTextColor(Color.WHITE)
                }
            }
        }
    }
}