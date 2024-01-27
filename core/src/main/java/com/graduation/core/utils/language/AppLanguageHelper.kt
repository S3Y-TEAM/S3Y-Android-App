package com.graduation.core.utils.language

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.LocaleList
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.graduation.core.utils.sharedPrefernces.SharedPreferenceHelper
import java.util.*

/** App Language **/
fun getCurrentAppLanguage(sharedPreferenceHelper: SharedPreferenceHelper): String {
    return sharedPreferenceHelper.appLanguage.ifEmpty {
        getDeviceLocaleLanguage()
    }
}

fun getDeviceLocaleLanguage(): String {
    return Locale.getDefault().language
}

/** Get app language from locales **/
fun getAppLanguage(mContext: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        mContext.getSystemService(LocaleManager::class.java).applicationLocales.toLanguageTags()
    } else {
        AppCompatDelegate.getApplicationLocales().toLanguageTags()
    }
}

/** Set app language to locales **/
fun setAppLanguage(activity: Activity, languageTag: String, restartActivity: Boolean = true) {
    //for android 13 and +
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        activity.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList(Locale.forLanguageTag(languageTag))
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))
    }

    if (restartActivity) restart(activity)
}

fun restart(activity: Activity) {
    val intent = Intent(activity, activity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    activity.startActivity(intent)
    activity.overridePendingTransition(0, 0)
    Handler(Looper.getMainLooper()).postDelayed({
        activity.recreate()
    }, 200) // wait for the animation to finish
}
