package com.graduation.presentation.screens.main.user.show.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation.domain.models.main.user.create.task.details.Applicants
import com.graduation.domain.models.main.user.create.task.details.ApplicantsItem
import com.graduation.domain.models.main.user.home.Task
import com.graduation.presentation.R
import com.graduation.presentation.databinding.ApplicantItemBinding
import com.graduation.presentation.screens.main.dev.home.first.DummyData

class ApplicantsAdapter(private val onItemClick: ((ApplicantsItem) -> Unit)? = null) :
    RecyclerView.Adapter<ApplicantsAdapter.AllApplicantsAdapter>() {

    inner class AllApplicantsAdapter(val binding: ApplicantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    private val differCallback = object : DiffUtil.ItemCallback<ApplicantsItem>() {
        override fun areItemsTheSame(oldItem: ApplicantsItem, newItem: ApplicantsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ApplicantsItem, newItem: ApplicantsItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllApplicantsAdapter {
        val binding =
            ApplicantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllApplicantsAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllApplicantsAdapter, position: Int) {
        val item = differ.currentList[position]

        holder.binding.apply {
            applicantName.text = "Suzan Abdien"
            applicantImage.setImageResource(R.drawable.ic_person)
            applicantDescription.text = "Android Developer Mobile Native with 4 year experience"

            root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }

    }

}