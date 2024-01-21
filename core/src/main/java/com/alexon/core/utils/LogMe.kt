package com.alexon.core.utils

import android.util.Log

fun logMe(tag: String = "TAG", msg: String) {
    val showLog = true
    if (showLog) Log.e(tag, msg)
}
