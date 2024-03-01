package com.graduation.core.utils

import android.content.Context
import android.widget.Toast

fun toastMe(context: Context , message:String){
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}