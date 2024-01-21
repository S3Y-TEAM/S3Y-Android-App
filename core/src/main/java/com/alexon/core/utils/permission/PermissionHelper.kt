package com.alexon.core.utils.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.alexon.core.utils.logMe
import com.alexon.core.utils.sharedPrefernces.SharedPreferenceHelper
import com.permissionx.guolindev.PermissionX


private const val maxNotificationCount = 2

fun AppCompatActivity.requestPermissions(
    permissionType: PermissionType,
    sharedPreferenceHelper: SharedPreferenceHelper? = null,
    doFirst: (() -> Unit)? = null,
    showRationaleDialog: Boolean = true,
    showSettingDialog: Boolean = true,
    onPermissionGranted: (() -> Unit)? = null,
    onPermissionRejected: (() -> Unit)? = null,
    onAny: (() -> Unit)? = null,
) {

    doFirst?.invoke()


    permissionType.permissions.forEach { permission ->
        logMe("PermissionHelper", permission)
        logMe("PermissionHelper", isPermissionDeclaredInManifest(this, permission).toString())
        if(!isPermissionDeclaredInManifest(this, permission)){
            throw RuntimeException("نسيت تعرف البرميشن يا اهطل ")
        }
    }


    if (permissionType == PermissionType.Notification) {
        //if device less than android 13 return
        if (Build.VERSION.SDK_INT < 33) {
            onAny?.invoke()
            return
        }

        //if notification count reach the max show notification
        if (sharedPreferenceHelper != null) {
            if (!shouldShowNotification(sharedPreferenceHelper)) {
                onAny?.invoke()
                return
            }
        } else {
            throw RuntimeException("if you use notification permission you have to parse sharedPreferenceHelper")
        }
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


private fun shouldShowNotification(sharedPreferenceHelper: SharedPreferenceHelper): Boolean {
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

fun isPermissionDeclaredInManifest(context: Context, permission: String): Boolean {
    val packageManager: PackageManager = context.packageManager
    val packageName: String = context.packageName

    // Get the list of all declared permissions
    val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
    val declaredPermissions = packageInfo.requestedPermissions ?: emptyArray()

    // Check if the desired permission is present in the list
    return permission in declaredPermissions
}

