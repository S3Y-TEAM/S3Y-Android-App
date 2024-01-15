package com.alexon.common.utils.sharedPrefernces

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.alexon.common.constants.Constants.SHARED_APP_LANGUAGE_KEY
import com.alexon.common.constants.Constants.SHARED_AUTH_TOKEN_KEY
import com.alexon.common.constants.Constants.SHARED_APP_KEY
import com.alexon.common.constants.Constants.SHARED_IS_GET_LOCATION
import com.alexon.common.constants.Constants.SHARED_USER_HOME_ADDRESS
import com.alexon.common.constants.Constants.SHARED_USER_LAT
import com.alexon.common.constants.Constants.SHARED_USER_LNG
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceHelper @Inject constructor(@ApplicationContext context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_APP_KEY, Context.MODE_PRIVATE)

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var openedTheAppBefore: Boolean
        get() = loadBoolean("openedTheAppBefore", false) ?: false
        set(value) = saveData("openedTheAppBefore", value)

    var token: String
        get() = loadData(SHARED_AUTH_TOKEN_KEY) ?: ""
        set(value) = saveData(SHARED_AUTH_TOKEN_KEY, value)

    val brearToken: String
        get() = "Bearer ${loadData(SHARED_AUTH_TOKEN_KEY)}"

    var appLanguage: String
        get() = loadData(SHARED_APP_LANGUAGE_KEY) ?: "ar"
        set(value) = saveData(SHARED_APP_LANGUAGE_KEY, value)

    var userLat: String
        get() = loadData(SHARED_USER_LAT) ?: "0.0"
        set(value) = saveData(SHARED_USER_LAT, value)

    var userLng: String
        get() = loadData(SHARED_USER_LNG) ?: "0.0"
        set(value) = saveData(SHARED_USER_LNG, value)

    var userHomeAddress: String
        get() = loadData(SHARED_USER_HOME_ADDRESS) ?: ""
        set(value) = saveData(SHARED_USER_HOME_ADDRESS, value)

    var isGetUserLocation: Boolean
        get() = loadBoolean(SHARED_IS_GET_LOCATION, false) ?: false
        set(value) = saveData(SHARED_IS_GET_LOCATION, value)


    //save String data
    fun saveData(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun saveData(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    //save Boolean data
    fun saveData(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    //save app language
    fun saveLang(value: String) {
        editor.putString(SHARED_APP_LANGUAGE_KEY, value)
        editor.apply()
    }

    //load String data
    fun loadData(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun loadInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    //load boolean data
    fun loadBoolean(
        key: String, defaultVal: Boolean
    ): Boolean? {
        return sharedPreferences.getBoolean(key, defaultVal)
    }

    //load app languages
    fun loadLang(): String {
        return sharedPreferences.getString(SHARED_APP_LANGUAGE_KEY, "")!!
    }

    @SuppressLint("CommitPrefEdits")
    fun clean() {
        sharedPreferences.edit()?.clear()?.apply()
    }

}