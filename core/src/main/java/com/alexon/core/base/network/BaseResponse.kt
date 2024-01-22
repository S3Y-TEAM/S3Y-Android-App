package com.alexon.core.base.network

import android.os.Parcelable
import com.alexon.core.base.network.Errors
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
open class BaseResponse(
    var result: @RawValue Any? = null,
    var message: String? = "",
    val status: @RawValue Any? = null,
    val errors: Errors? = null,
) : Parcelable

