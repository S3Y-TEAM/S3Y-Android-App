package com.graduation.core.utils.sharedPrefernces

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceHelper @Inject constructor(@ApplicationContext context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_APP_KEY, Context.MODE_PRIVATE)

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var openedTheAppBefore: Boolean
        get() = loadData(SHARED_OPENED_APP_BEFORE, false)
        set(value) = saveData(SHARED_OPENED_APP_BEFORE, value)

    var sharedRole: String
        get() = loadData(SHARED_ROLE, "")
        set(value) = saveData(SHARED_ROLE, value)

    var username: String
        get() = loadData(SHARED_USERNAME, "")
        set(value) = saveData(SHARED_USERNAME, value)

    var appLanguage: String
        get() = loadData(SHARED_APP_LANGUAGE_KEY) ?: "ar"
        set(value) = saveData(SHARED_APP_LANGUAGE_KEY, value)

    var userLat: Float
        get() = loadData(SHARED_USER_LAT) ?: 0.0f
        set(value) = saveData(SHARED_USER_LAT, value)

    var userLng: Float
        get() = loadData(SHARED_USER_LNG) ?: 0.0f
        set(value) = saveData(SHARED_USER_LNG, value)

    var userHomeAddress: String
        get() = loadData(SHARED_USER_HOME_ADDRESS) ?: ""
        set(value) = saveData(SHARED_USER_HOME_ADDRESS, value)


    var notificationCount: Int
        get() = loadData("NOTIFICATION_COUNT") ?: 1
        set(value) = saveData("NOTIFICATION_COUNT", value)

    private inline fun <reified T> saveData(key: String, value: T? = null) {
        when (T::class) {
            String::class -> editor.putString(key, value as? String ?: "")
            Boolean::class -> editor.putBoolean(key, value as? Boolean ?: false)
            Int::class -> editor.putInt(key, value as? Int ?: 0)
            Float::class -> editor.putFloat(key, value as? Float ?: 0f)
            Long::class -> editor.putLong(key, value as? Long ?: 0L)
        }
        editor.apply()
    }

    private inline fun <reified T> loadData(key: String, defaultValue: T? = null): T {
        return when (T::class) {
            String::class -> {
                sharedPreferences.getString(key, defaultValue as? String ?: "") as T
            }

            Boolean::class -> {
                sharedPreferences.getBoolean(key, defaultValue as? Boolean ?: false) as T
            }

            Int::class -> sharedPreferences.getInt(key, defaultValue as? Int ?: 0) as T
            Float::class -> sharedPreferences.getFloat(key, defaultValue as? Float ?: 0f) as T
            Long::class -> sharedPreferences.getLong(key, defaultValue as? Long ?: 0L) as T
            else -> sharedPreferences.getString(key, defaultValue as? String ?: "") as T
        }
    }

    fun clean(keepAppSettings: Boolean) {
        sharedPreferences.edit()?.clear()?.apply()
    }

    companion object {
        //shared
        const val SHARED_PREFERENCE_APP_KEY = "Tavolo"
        const val SHARED_FCM_DEVICE_TOKEN = "SHARED_FCM_DEVICE_TOKEN"
        const val SHARED_USER_LAT = "SHARED_USER_LAT"
        const val SHARED_USER_LNG = "SHARED_USER_LNG"
        const val SHARED_USER_HOME_ADDRESS = "SHARED_USER_HOME_ADDRESS"
        const val SHARED_APP_LANGUAGE_KEY = "SHARED_APP_LANGUAGE_KEY"
        const val SHARED_OPENED_APP_BEFORE = "SHARED_OPENED_APP_BEFORE"
        const val SHARED_ROLE = "SHARED_ROLE"
        const val SHARED_USERNAME = "SHARED_USERNAME"
    }
}