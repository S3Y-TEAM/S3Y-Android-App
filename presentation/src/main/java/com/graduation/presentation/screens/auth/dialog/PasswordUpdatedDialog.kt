package com.graduation.presentation.screens.auth.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.graduation.core.extensions.navigation.navigateTo
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentPasswordUpdatedDialogBinding


class PasswordUpdatedDialog : DialogFragment() {
    private lateinit var binding: FragmentPasswordUpdatedDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPasswordUpdatedDialogBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requireActivity().setFinishOnTouchOutside(true)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        this.dialog!!.window!!.setLayout(((9 * width) / 10), (4 * height) / 10)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.backLogin.setOnClickListener {
            navigateTo(R.id.action_passwordUpdatedDialog_to_loginFragment)
        }
    }

}