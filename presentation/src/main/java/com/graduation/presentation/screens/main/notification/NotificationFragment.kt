package com.graduation.presentation.screens.main.notification

import android.graphics.*
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.toastMe
import com.graduation.presentation.R
import com.graduation.presentation.databinding.FragmentNotificationBinding
import com.graduation.presentation.screens.BaseFragmentImpl
import com.graduation.presentation.screens.main.dev.home.HomeViewModel
import com.graduation.presentation.screens.main.dev.home.first.DummyData
import com.graduation.presentation.screens.main.notification.adapter.NotificationAdapter

class NotificationFragment :
    BaseFragmentImpl<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    override val sharedViewModel: SharedViewModel by activityViewModels()
    private val paint = Paint()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setAppBar()
        sharedViewModel.setTextAppBar("Notifications")

        setupRV()


    }

    override fun setOnClickListener() {
    }

    override fun setAppBar() {
        changeStatusBarColor(R.color.white, isContentLight = false, isTransparent = false)
    }

    override fun onLoadingStart() {
    }

    override fun onComplete(isSuccess: Boolean) {
    }

    override fun onCancel() {
    }

    private fun setupRV() {
        val adapterItems = NotificationAdapter()
        val listOfData = dummyData()
        adapterItems.differ.submitList(listOfData)
        binding.notificationRv.apply {
            adapter = adapterItems
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                val itemView: View = viewHolder.itemView
                val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                val width = height / 3
                val sizeText = 48f // Adjust text size as needed
                val textPaint = Paint().apply {
                    color = Color.WHITE
                    textSize = sizeText
                    textAlign = Paint.Align.CENTER
                }
                val textY =
                    itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat()) / 2 + sizeText / 3

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX > 0) {
                        // Swipe right
                        paint.color = Color.parseColor("#388E3C")
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, paint)

                        // Draw pin icon
                        val icon =
                            ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_pin_)
                        if (icon != null) {
                            val iconMargin = (height - icon.intrinsicHeight) / 2
                            val iconTop =
                                itemView.top + (itemView.bottom - itemView.top - icon.intrinsicHeight) / 2
                            val iconLeft = itemView.left + iconMargin
                            val iconRight = iconLeft + icon.intrinsicWidth
                            val iconBottom = iconTop + icon.intrinsicHeight
                            icon.setBounds(iconLeft.toInt(), iconTop, iconRight.toInt(), iconBottom)
                            icon.draw(c)
                        }

                        // Draw pin text
                        val textX = itemView.left.toFloat() + 3 * width
                        c.drawText("Pin", textX, textY, textPaint)
                    } else {
                        // Swipe left
                        paint.color = Color.parseColor("#E25C5C")
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, paint)

                        // Draw delete icon
                        val icon =
                            ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
                        if (icon != null) {
                            val iconMargin = (height - icon.intrinsicHeight) / 2
                            val iconTop =
                                itemView.top + (itemView.bottom - itemView.top - icon.intrinsicHeight) / 2
                            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                            val iconRight = itemView.right - iconMargin
                            val iconBottom = iconTop + icon.intrinsicHeight
                            icon.setBounds(iconLeft.toInt(), iconTop, iconRight.toInt(), iconBottom)
                            icon.draw(c)
                        }

                        // Draw delete text
                        val textX = itemView.right.toFloat() - 3 * width
                        c.drawText("Delete", textX, textY, textPaint)
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    adapterItems.deleteItem(position)
                    Snackbar.make(binding.notificationRv, "Item deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            adapterItems.restoreItem()
                        }.show()
                } else if (direction == ItemTouchHelper.RIGHT) {
                    adapterItems.pinItem(position)
                    toastMe(context = requireContext(), message = "Item pinned")
                }
            }
        }).attachToRecyclerView(binding.notificationRv)
    }

    private fun dummyData(): MutableList<DummyData> {
        return mutableListOf(
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "1 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "2 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "3 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "4 I wanted Developer to make me android application for my graduationI wanted Developer to make me android application for my graduation I wanted Developer to make me android application for my graduation I wanted Developer to make me android application for my graduation I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "5 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "6 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "7 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "8 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "9 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            ),
            DummyData(
                name = "Suzan Abdien",
                title = "Android Application",
                description = "10 I wanted Developer to make me android application for my graduation ",
                image = R.drawable.app_logo
            )

        )
    }


}



