package com.alexon.core.utils.sharedPrefernces

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedSharedPreference @Inject constructor(@ApplicationContext context: Context) {

    private val spec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .build()

    private val masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        ENCRYPTED_SHARED_PREFERENCE_APP_KEY,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val editor: SharedPreferences.Editor = prefs.edit()

    var token: String
        get() = loadData(SHARED_AUTH_TOKEN_KEY) ?: ""
        set(value) = saveData(SHARED_AUTH_TOKEN_KEY, value)

    val brearToken: String
        get() = "Bearer ${loadData<String>(SHARED_AUTH_TOKEN_KEY)}"

    var userData: SharedUser
        get() = loadUserData()
        set(value) = saveUserData(value)


    private fun saveUserData(value: SharedUser?) {
        val json = Gson().toJson(value)
        saveData(USER_DATA, json)
    }

    private fun loadUserData(): SharedUser {
        val value = loadData<String>(USER_DATA)
        val innerUser = Gson().fromJson(value, SharedUser::class.java)
        return innerUser ?: SharedUser()
    }

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
                prefs.getString(key, defaultValue as? String ?: "") as T
            }

            Boolean::class -> {
                prefs.getBoolean(key, defaultValue as? Boolean ?: false) as T
            }

            Int::class -> prefs.getInt(key, defaultValue as? Int ?: 0) as T
            Float::class -> prefs.getFloat(key, defaultValue as? Float ?: 0f) as T
            Long::class -> prefs.getLong(key, defaultValue as? Long ?: 0L) as T
            else -> prefs.getString(key, defaultValue as? String ?: "") as T
        }
    }

    fun clean() {
        prefs.edit()?.clear()?.apply()
    }

    companion object {
        //shared
        const val ENCRYPTED_SHARED_PREFERENCE_APP_KEY = "Encrypted-Tavolo"
        const val SHARED_AUTH_TOKEN_KEY = "SHARED_AUTH_TOKEN_KEY"
        const val USER_DATA = "USER_DATA"
    }
}