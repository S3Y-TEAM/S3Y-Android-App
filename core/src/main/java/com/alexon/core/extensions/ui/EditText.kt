package com.alexon.core.extensions.ui

import android.text.InputFilter
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.doOnTextChanged
import com.alexon.core.R

fun EditText.setMaxLength(length: Int) {
    filters += InputFilter.LengthFilter(length)
}

fun EditText.changeStartDrawableWhenActive(
    length: Int,
    drawable: Int,
    colorWhenActive: Int = R.color.black,
    colorWhenDisActive: Int = R.color.black
) {

    //TODO add your localization method
    // eg.val isArabic = (SharedPreferenceHelper().appLanguage == APP_LANGUAGE_AR)
    val isArabic = true

    val drawableActive = DrawableCompat.wrap(AppCompatResources.getDrawable(context, drawable)!!)
        .mutate()
        .also { DrawableCompat.setTint(it, ContextCompat.getColor(context, colorWhenActive)) }

    val drawableDisActive = DrawableCompat.wrap(AppCompatResources.getDrawable(context, drawable)!!)
        .mutate()
        .also { DrawableCompat.setTint(it, ContextCompat.getColor(context, colorWhenDisActive)) }

    if (length > 0) {
        setCompoundDrawablesWithIntrinsicBounds(
            if (isArabic) null else drawableActive,
            null,
            if (isArabic) drawableActive else null,
            null
        )
    }

    if (length == 0) {
        setCompoundDrawablesWithIntrinsicBounds(
            if (isArabic) null else drawableDisActive,
            null,
            if (isArabic) drawableDisActive else null,
            null
        )
    }

}



inline fun EditText.doOnTextChangedIfNotBlankOrNull(crossinline action: (text: String) -> Unit) {
    this.doOnTextChanged { text, _, _, _ ->
        text?.let {
            val str = it.toString()
            if (str.isNotBlank()) action(str)
        }
    }
}

fun EditText.setTextWithCursorPosition(text: String) {
    this.setText(text)
    this.setSelection(text.length)
}