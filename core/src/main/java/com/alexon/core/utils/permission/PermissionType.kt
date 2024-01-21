package com.alexon.core.utils.permission

import android.Manifest
import android.annotation.SuppressLint
import com.alexon.core.R

enum class PermissionType(
    val permissions: List<String>,
    val rationaleMessage: Int,
    val rationalePositiveText: Int,
    val rationaleNegativeText: Int,
    val settingMessage: Int,
    val settingPositiveText: Int,
    val settingNegativeText: Int
) {

    @SuppressLint("InlinedApi")
    Notification(
        permissions = listOf(
            Manifest.permission.POST_NOTIFICATIONS
        ),
        rationaleMessage = R.string.permission_notification_rationale,
        rationalePositiveText = R.string.ok,
        rationaleNegativeText = R.string.cancel,
        settingMessage = R.string.permission_notification_settings,
        settingPositiveText = R.string.ok,
        settingNegativeText = R.string.cancel
    ),

    Location(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
        rationaleMessage = R.string.app_name,
        rationalePositiveText = R.string.app_name,
        rationaleNegativeText = R.string.app_name,
        settingMessage = R.string.app_name,
        settingPositiveText = R.string.app_name,
        settingNegativeText = R.string.app_name
    ),

    Storage(
        permissions = listOf(),
        rationaleMessage = R.string.app_name,
        rationalePositiveText = R.string.app_name,
        rationaleNegativeText = R.string.app_name,
        settingMessage = R.string.app_name,
        settingPositiveText = R.string.app_name,
        settingNegativeText = R.string.app_name
    )
}