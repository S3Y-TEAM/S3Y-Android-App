package com.alexon.presentation.components.editText

sealed class LTEditTextStatus {
    object Disabled : LTEditTextStatus()
    object Enabled : LTEditTextStatus()
    object Loading : LTEditTextStatus()
    object Error : LTEditTextStatus()
}