package com.alexon.common.utils

import com.alexon.common.utils.logMe

fun tryNow(
    tag: String = "TryNowError",
    onCatch: ((Exception) -> Unit)? = null,
    action: () -> Unit,
) {
    try {
        action()
    } catch (e: Exception) {
        logMe(tag = tag, msg = e.message ?: "Catch!")
        onCatch?.invoke(e)
    }
}