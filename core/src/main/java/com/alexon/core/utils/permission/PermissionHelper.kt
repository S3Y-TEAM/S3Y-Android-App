package com.alexon.core.utils.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.alexon.core.utils.sharedPrefernces.SharedPreferenceHelper
import com.permissionx.guolindev.PermissionX

private const val maxNotificationCount = 2

fun AppCompatActivity.requestPermissions(
    permissionType: PermissionType,
    sharedPreferenceHelper: SharedPreferenceHelper? = null,
    onPreExecute: (() -> Unit)? = null,
    showRationaleDialog: Boolean = true,
    showSettingDialog: Boolean = true,
    onPermissionGranted: (() -> Unit)? = null,
    onPermissionRejected: (() -> Unit)? = null,
    onAny: (() -> Unit)? = null,
) {

    onPreExecute?.invoke()

    isPermissionDeclaredInManifest(permissionType.permissions)

    //Notification Conditions
    if (permissionType == PermissionType.Notification &&
        !checkAndShowNotificationIfRequired(sharedPreferenceHelper, onAny)
    ) {
        return
    }

    PermissionX.init(this).permissions(
        permissionType.permissions
    ).onExplainRequestReason { scope, deniedList ->
        if (showRationaleDialog) {
            scope.showRequestReasonDialog(
                deniedList,
                getString(permissionType.rationaleMessage),
                getString(permissionType.rationalePositiveText),
                getString(permissionType.rationaleNegativeText),
            )
        }
    }.onForwardToSettings { scope, deniedList ->
        if (showSettingDialog) {
            scope.showForwardToSettingsDialog(
                deniedList,
                getString(permissionType.settingMessage),
                getString(permissionType.settingPositiveText),
                getString(permissionType.settingNegativeText),
            )
        }
    }.request { allGranted, _, _ ->
        if (allGranted) {
            onPermissionGranted?.invoke()
            onAny?.invoke()
        } else {
            onPermissionRejected?.invoke()
            onAny?.invoke()
        }
    }
}

/** Manifest **/
fun AppCompatActivity.isPermissionDeclaredInManifest(permissions: List<String>) {
    permissions.forEach { permission ->
        if (!isPermissionDeclaredInManifest(this, permission)) {
            throw RuntimeException("Permission $permission not declared in manifest for package $packageName\"")
        }
    }
}

fun isPermissionDeclaredInManifest(context: Context, permission: String): Boolean {
    val packageManager: PackageManager = context.packageManager
    val packageName: String = context.packageName

    // Get the list of all declared permissions
    val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
    val declaredPermissions = packageInfo.requestedPermissions ?: emptyArray()

    // Check if the desired permission is present in the list
    return permission in declaredPermissions
}

/** Notification **/
fun checkAndShowNotificationIfRequired(
    sharedPreferenceHelper: SharedPreferenceHelper?,
    onAny: (() -> Unit)?
): Boolean {
    //if device less than android 13 return
    if (Build.VERSION.SDK_INT < 33) {
        onAny?.invoke()
        return false
    }

    //if user not parsed sharedPref
    if (sharedPreferenceHelper == null) {
        throw RuntimeException("If you use notification permission, you have to provide SharedPreferenceHelper")
    }

    //if notification count reach the max show notification
    if (!shouldShowNotificationRequest(sharedPreferenceHelper)) {
        onAny?.invoke()
        return false
    }

    return true
}

private fun shouldShowNotificationRequest(sharedPreferenceHelper: SharedPreferenceHelper): Boolean {
    //if not reach the count
    if (sharedPreferenceHelper.notificationCount < maxNotificationCount) {
        sharedPreferenceHelper.notificationCount += 1
        return false
    }

    //if reach the max count
    if (sharedPreferenceHelper.notificationCount == maxNotificationCount) {
        sharedPreferenceHelper.notificationCount = 1
        return true
    }

    return false
}