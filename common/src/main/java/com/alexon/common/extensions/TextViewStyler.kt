package com.alexon.common.extensions

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.*
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.alexon.common.utils.tryNow

fun TextView.styleWord(
    selectedText: String = "",
    ignoreCase: Boolean = true,
    mFirstIndex: Int = -1,
    mLastIndex: Int = -1,
    @ColorRes color: Int? = null,
    typeFace: Int? = null,
    underlined: Boolean? = null,
    sizePortion: Float = 0.0f,
    onError: (() -> Unit)? = null
) {
    val spannable = SpannableStringBuilder(this.text)

    val firstIndex: Int
    val lastIndex: Int

    //if entered word
    if (mFirstIndex == -1 && mLastIndex == -1 && selectedText.isNotEmpty()) {
        firstIndex = this.text.indexOf(selectedText, ignoreCase = ignoreCase)
        lastIndex = firstIndex + selectedText.length
    } else {
        //if entered index
        firstIndex = mFirstIndex
        lastIndex = mLastIndex
    }

    tryNow(
        action = {

            //color
            if (color != null) {
                spannable.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(context, color)
                    ), firstIndex, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //TypeFace / Weight
            if (typeFace != null) {
                spannable.setSpan(
                    StyleSpan(typeFace),
                    firstIndex,
                    lastIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //Underline
            if (underlined != null) {
                spannable.setSpan(
                    UnderlineSpan(),
                    firstIndex,
                    lastIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //size
            if (sizePortion != 0.0f) {
                spannable.setSpan(
                    RelativeSizeSpan(sizePortion),
                    firstIndex,
                    lastIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //set text
            setText(spannable, TextView.BufferType.SPANNABLE)
        },
        onCatch = {
            onError?.invoke()
        }
    )
}