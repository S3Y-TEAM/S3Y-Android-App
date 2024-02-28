package com.graduation.presentation.screens.auth.categories.adapter

import android.app.ActionBar.LayoutParams
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.core.extensions.getResourceColor
import com.graduation.domain.models.auth.Auth.categories.CategoriesResponseItem
import com.graduation.presentation.Constants.CATEGORIES_MAX_NUMBER
import com.graduation.presentation.R
import com.graduation.presentation.databinding.CategoriesItemBinding

class CategoriesAdapter( var kind: Boolean) :
    RecyclerView.Adapter<CategoriesAdapter.AllCategoriesAdapter>() {

    var chooseCategory = arrayListOf<Int>()
    var selectedExperience = -1
    var experience = ""

    inner class AllCategoriesAdapter(val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        }

    private val differCallback = object : DiffUtil.ItemCallback<CategoriesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: CategoriesResponseItem,
            newItem: CategoriesResponseItem,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CategoriesResponseItem,
            newItem: CategoriesResponseItem,
        ): Boolean {
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
            when (kind) {
                true -> {
                    layoutParams.width = LayoutParams.WRAP_CONTENT
                    holder.binding.btnItem.layoutParams.width = LayoutParams.WRAP_CONTENT


                    if ((chooseCategory.contains(item.id)))
                        addedItemStyle(holder = holder)
                    else
                        removedItemStyle(holder = holder)


                    holder.binding.apply {
                        btnItem.text = item.name
                    }
                    setOnClickListener {
                        if (chooseCategory.size < CATEGORIES_MAX_NUMBER) {
                            if (!(chooseCategory.contains(item.id)))
                                addItem(holder = holder, item = item)
                            else
                                removeItem(holder = holder, item = item)
                        } else if (chooseCategory.size == CATEGORIES_MAX_NUMBER) {
                            if ((chooseCategory.contains(item.id)))
                                removeItem(holder = holder, item = item)
                            else
                                Toast.makeText(context, "Categories Completed", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                }

                false -> {
                    layoutParams.width = LayoutParams.MATCH_PARENT
                    holder.binding.btnItem.layoutParams.width = LayoutParams.MATCH_PARENT
                    holder.binding.apply {
                        btnItem.text = item.name
                    }

                    if (selectedExperience == -1)
                        removedItemStyle(holder)

                    setOnClickListener {
                        when (selectedExperience) {
                            -1 -> {
                                selectedExperience = item.id
                                experience = item.name
                                addedItemStyle(holder = holder)
                            }
                            item.id -> {
                                removedItemStyle(holder)
                                selectedExperience = -1
                                experience = ""
                            }
                            else -> {
                                Toast.makeText(context, "Select maximum one Experience", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addItem(
        holder: AllCategoriesAdapter, item: CategoriesResponseItem,
    ) {
        chooseCategory.add(item.id)
        addedItemStyle(holder = holder)
    }

    private fun removeItem(
        holder: AllCategoriesAdapter, item: CategoriesResponseItem,
    ) {
        chooseCategory.remove(item.id)
        removedItemStyle(holder = holder)
    }

    private fun removedItemStyle(
        holder: AllCategoriesAdapter,
    ) {
        holder.binding.apply {
            btnItem.setBackgroundColor(Color.WHITE)
            btnItem.setTextColor(
                btnItem.context.getResourceColor(
                    R.color.main_color
                )
            )
        }
    }

    private fun addedItemStyle(
        holder: AllCategoriesAdapter,
    ) {
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