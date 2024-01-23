package com.alexon.presentation.components.editText

import android.os.Parcelable
import android.view.View
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class LtEditTextState(
    val parcel: Parcelable?,
    val text: String = "",
    val textColor: Int,
) : Parcelable, View.BaseSavedState(parcel)