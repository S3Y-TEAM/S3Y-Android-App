package com.graduation.core.base.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
open class BaseResponse(
    var result: @RawValue Any? = null,
    var message: String? = "",
    val status: @RawValue Any? = null,
    val errors: Errors? = null,
) : Parcelable

