package com.alexon.core.extensions.dataTypes

import java.math.RoundingMode

/**
 * This methods used to round and scale numbers
 * eg. 20.1231651 to 20.12
 * eg. 20.1251651 to 20.13
 */
fun Double.toSuitableDecimal(scale: Int = 2) =
    toBigDecimal().setScale(scale, RoundingMode.UP).toString()

fun Float.toSuitableDecimal(scale: Int = 2) =
    toBigDecimal().setScale(scale, RoundingMode.UP).toString()

/**
 * This methods used to scale then remove zeros from the end of the string
 * eg. "20.9900" to "20.99"
 * eg. "20.9900" to "20.99"
 * eg. "20.00000" to "20"
 **/
fun String.removeTrailingZeros() {
    orEmpty().trimEnd { it == '0' }.trimEnd { it == '.' }.trimEnd { it == ',' }
}

fun Float.removeTrailingZeros(): String {
    val stringValue = this.toSuitableDecimal(2)
    return if (stringValue.contains('.')) {
        stringValue.replace("0*$".toRegex(), "").removeSuffix(".")
    } else {
        stringValue
    }
}

fun Double.removeTrailingZeros(): String {
    val stringValue = this.toSuitableDecimal(2)
    return if (stringValue.contains('.')) {
        stringValue.replace("0*$".toRegex(), "").removeSuffix(".")
    } else {
        stringValue
    }
}
