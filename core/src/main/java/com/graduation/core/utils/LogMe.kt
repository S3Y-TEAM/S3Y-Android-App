package com.graduation.core.utils

import android.util.Log

fun logMe(tag: String = "TAG", message: String) {
    val showLog = true
    if (showLog) Log.e(tag, message)
}
