package com.graduation.presentation.screens.main.user.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentAddTaskBinding
import com.graduation.presentation.screens.BaseFragmentImpl

class AddTaskFragment :
    BaseFragmentImpl<FragmentAddTaskBinding>(FragmentAddTaskBinding::inflate) {

    override val viewModel: AddTaskViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        setUi()
    }

    private fun setUi() {
        binding.skilled.roleImage.setImageResource(R.drawable.onboarding_2)
        binding.skilled.roleName.text = resources.getText(R.string.skilled_worker)
        binding.skilled.roleName.setTextColor(resources.getColor(R.color.white))

        bringToFrontAndResize(binding.developer.root, binding.skilled.root)

    }

    override fun setOnClickListener() {
        binding.developer.root.setOnClickListener {
            bringToFrontAndResize(binding.developer.root, binding.skilled.root)
            binding.skilled.roleName.textSize = resources.getDimension(R.dimen.small_text)
            binding.developer.roleName.textSize = resources.getDimension(R.dimen.resize_text)
        }

        binding.skilled.root.setOnClickListener {
            bringToFrontAndResize(binding.skilled.root, binding.developer.root)
            binding.developer.roleName.textSize = resources.getDimension(R.dimen.small_text)
            binding.skilled.roleName.textSize = resources.getDimension(R.dimen.resize_text)
        }

        binding.roleNextButton.setOnClickListener {
            navigateTo(R.id.action_placeholder_to_addTaskDetailsFragment)
        }

    }

    private fun bringToFrontAndResize(frontCard: View, backCard: View) {
        // Bring the clicked card to the front
        binding.frameLayout.bringChildToFront(frontCard)

        // Resize the front card to its original size
        frontCard.layoutParams.width = resources.getDimensionPixelSize(R.dimen.card_size_width)
        frontCard.layoutParams.height = resources.getDimensionPixelSize(R.dimen.card_size_height)
        frontCard.requestLayout()

        // Resize the back card to be smaller (6dp height)
        backCard.layoutParams.width = resources.getDimensionPixelSize(R.dimen.small_card_weight)
        backCard.layoutParams.height = resources.getDimensionPixelSize(R.dimen.small_card_height)
        backCard.requestLayout()

        binding.frameLayout.invalidate()
    }

    override fun setAppBar() {
        sharedViewModel.setTextAppBar("Add Task")
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }


}