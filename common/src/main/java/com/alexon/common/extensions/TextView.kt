package com.alexon.common.extensions

import android.annotation.SuppressLint
import android.graphics.Paint
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.alexon.common.utils.tryNow


/** lines **/
fun TextView.setUnderLine(show: Boolean) {
    paintFlags = if (show) {
        paintFlags or Paint.UNDERLINE_TEXT_FLAG
    } else {
        paintFlags and Paint.UNDERLINE_TEXT_FLAG
    }
}

fun TextView.setThroughLine(show: Boolean) {
    paintFlags = if (show) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG
    }
}

/** coloring **/
fun TextView.setColorOfText(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

@SuppressLint("SetTextI18n")
fun TextView.hideNumberWithStarts(originalText : String , phoneNumber: String) {
    val masked = phoneNumber.mapIndexed { index, c ->
        if (phoneNumber.length - index > 2) "*" else c
    }.joinToString("")

    text = "$originalText $masked"
}

/**
 * This method used to color
 * 1- Word :
 *        by using @param selectedText only and ignore mFirstIndex , mLastIndex
 * 2- Multi Word in sequence :
 *        by using @param mFirstIndex , mLastIndex and ignore selectedText
 *
 * @param fullText the full sentence that will be shown in textView
 * @param colorId the color of selected word
 * @param selectedText the selected word you want to color
 * @param ignoreCase if you make it true it will avoid big letters and crash but
 * if you need to make it case sensitive make it false and handle @param onError
 * @param mFirstIndex the first position from where you want to start coloring
 * @param mLastIndex the last position form where you want to end coloring
 * @param onError if you enter wrong indexes or wrong word it will throw exception
 *
 * Warning 1: This method is case sensitive if you try to color word "android" and in the sentence
 * its written "Android" it will throw exception so use @param ignoreCase true to avoid this
 *
 * Warning 2 : If you have two words with same letters eg. "Anas Test anas mohammed"
 * it will color first word only if you need to color the second word then use mFirstIndex , mLastIndex
 */
fun TextView.colorSelectedTextFromSentence(
    fullText: String,
    colorId: Int,
    selectedText: String = "",
    ignoreCase: Boolean = true,
    mFirstIndex: Int = -1,
    mLastIndex: Int = -1,
    onError: (() -> Unit)? = null
) {
    val spannable = SpannableString(fullText)

    var firstIndex = 0
    var lastIndex = 0

    //if entered word
    if (mFirstIndex == -1 && mLastIndex == -1 && selectedText.isNotEmpty()) {
        firstIndex = fullText.indexOf(selectedText, ignoreCase = ignoreCase)
        lastIndex = firstIndex + selectedText.length
    } else {
        //if entered index
        firstIndex = mFirstIndex
        lastIndex = mLastIndex
    }

    tryNow(
        action = {
            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, colorId)),
                firstIndex,
                lastIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            //set text
            setText(spannable, TextView.BufferType.SPANNABLE)
        },
        onCatch = {
            text = fullText
            onError?.invoke()
        }
    )
}

/**
 * Helper data class used with function colorWordsWithClicks()
 *
 * @param word the word you want to color
 * @param color the color of selected word
 * @param isUnderLined if you want to make word underlined
 */
data class ColoredWord(
    val word: String,
    val color: Int,
    val isUnderLined: Boolean,
    @FontRes val fontResId: Int = 0
)

/**
 * This method used to color word or list of words with click
 *
 * @param words list of words to be colored
 *
 * eg.
 *  tv.text = "Anas Mohammed send you friend request"
 *  tv.colorWordsWithClicks(
 *      listOf(
 *         Pair(ColoredWord("Anas Mohammed", R.color.red, false), View.OnClickListener {
 *                Toast.makeText(this, "anas", Toast.LENGTH_LONG).show()
 *         }),
 *      )
 *  )
 */
fun TextView.colorWordsWithClicks(words: List<Pair<ColoredWord, View.OnClickListener>>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1

    for (link in words) {

        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                // use this to change the link color
                textPaint.color = ContextCompat.getColor(context, link.first.color)
                // toggle below value to enable/disable
                // the underline shown below the clickable text
                textPaint.isUnderlineText = link.first.isUnderLined

                if (link.first.fontResId != 0) {
                    val typeface = ResourcesCompat.getFont(context, link.first.fontResId)
                    textPaint.typeface = typeface
                }
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first.word, startIndexOfLink + 1)

        spannableString.setSpan(
            clickableSpan,
            startIndexOfLink,
            startIndexOfLink + link.first.word.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

/**
 * This method used to clear spannable that used in this functions
 * - colorWordsWithClicks()
 * - colorSelectedTextFromSentence()
 */
fun TextView.clearSpannable() {
    tryNow {
        val spannableString = SpannableString(this.text)
        val spans = spannableString.getSpans(0, spannableString.length, Object::class.java)
        for (span in spans) {
            spannableString.removeSpan(span)
        }
        this.text = spannableString
    }
}

/** text **/
fun TextView.setHtmlText(htmlText: String) {
    text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT);
}

