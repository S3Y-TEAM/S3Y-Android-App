package com.graduation.presentation.components.editText

sealed class LTEditTextStatus {
    object Disabled : LTEditTextStatus()
    object Enabled : LTEditTextStatus()
    object Loading : LTEditTextStatus()
    object Error : LTEditTextStatus()
}