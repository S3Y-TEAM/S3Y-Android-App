package com.alexon.common.utils

import android.util.Log

fun logMe(msg: String, tag: String = "TAG") {
    val showLog = true
    if (showLog) Log.e(tag, msg)
}
