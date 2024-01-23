package com.alexon.presentation.components.editText

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alexon.core.extensions.getResourceColor
import com.alexon.core.utils.tryNow
import com.alexon.presentation.R
import com.google.android.material.card.MaterialCardView

class LTEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var container: MaterialCardView
    private lateinit var editText: EditText
//    private lateinit var errorText: TextView
//    private lateinit var countryCodeTv: TextView
//    private lateinit var countryFlagIv: ImageView
//    private lateinit var countryPicker: View
//    private lateinit var underlineView: View
//    private lateinit var divider: View
//    private lateinit var progressBar: ProgressBar


    companion object {
        //colors
        val DEFAULT_TEXT_COLOR = R.color.blue_6
        val DEFAULT_HINT_COLOR = R.color.gray_2
        val DEFAULT_ERROR_COLOR = R.color.red_5


        // const val DEFAULT_TEXT_INPUT_TYPE = 0x00000001

//        const val DEFAULT_TEXT_ALIGNMENT = 5 // viewStart
//        const val DEFAULT_TEXT_GRAVITY = Gravity.CENTER
//        const val DEFAULT_ENABLE_COUNTRY_PICKER = false
    }

    /** Text **/
    var text: String = ""
        set(value) {
            field = value
            editText.setText(value)
        }
        get() {
            return editText.text.toString()
        }

    var textColor: Int = ContextCompat.getColor(this.context, DEFAULT_TEXT_COLOR)
        set(value) {
            field = value
            editText.setTextColor(value)
        }

    /** Hint **/
    var hint: String = ""
        set(value) {
            field = value
            editText.hint = value
        }

    var hintColor: Int = ContextCompat.getColor(this.context, DEFAULT_HINT_COLOR)
        set(value) {
            field = value
            editText.setHintTextColor(value)
        }

    /** Error **/
    var errorText: String = ""
        set(value) {
            field = value
            editText.hint = value
        }

    var errorTextColor: Int = ContextCompat.getColor(this.context, DEFAULT_ERROR_COLOR)
        set(value) {
            field = value
            editText.setHintTextColor(value)
        }

    var edTextHeight: Int = resources.getDimensionPixelSize(R.dimen.edit_text_height)
        set(value) {
            field = value
            container.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value)
        }

//    var maxLength: Int? = null
//        set(value) {
//            field = value
//            editText.filters = arrayOf(InputFilter.LengthFilter(value ?: Int.MAX_VALUE))
//        }

//    var mainLayoutDirection: Int = 3
//        set(value) {
//            field = value
//            container.layoutDirection = value
//        }

//    var etTextAlignment: Int = DEFAULT_TEXT_ALIGNMENT
//        set(value) {
//            field = value
//            mainEditText.textAlignment = value
//        }
//
//    var etInputType: Int = DEFAULT_TEXT_INPUT_TYPE
//        set(value) {
//            field = value
//            mainEditText.inputType = value
//        }
//
//    var etGravity: Int = DEFAULT_TEXT_GRAVITY
//        set(value) {
//            field = value
//            mainEditText.gravity = value
//        }

//    var fontFamilyId: Int = 0

//    var enableCountryPicker: Boolean = false
//        set(value) {
//            field = value
//            if (value) {
//                progressBar.visibility = View.VISIBLE
//                countryCodeTv.visibility = View.GONE
//                countryFlagIv.visibility = View.GONE
//                divider.visibility = View.VISIBLE
//            } else {
//                progressBar.visibility = View.GONE
//                countryCodeTv.visibility = View.GONE
//                countryFlagIv.visibility = View.GONE
//                divider.visibility = View.GONE
//            }
//        }


    init {
        setUp(attrs, defStyle)
    }


    /** helper methods **/
//    fun setOnTextChange(action: (text: String, length: Int) -> Unit) {
//        editText.doOnTextChanged { text, _, _, _ ->
//            action(text?.toString() ?: "", text?.length ?: 0)
//        }
//    }

//    fun removeOverNumber(maxNumber: Int) {
//        mainEditText.removeOverNumber(maxNumber)
//    }


//    fun isEditTextEnabled(isEnabled: Boolean) {
//        editText.isEnabled = isEnabled
//    }

//    fun setCountry(code: String, flag: String) {
//        progressBar.visibility = View.GONE
//
//        //flag
//        countryFlagIv.loadImage(flag)
//        animateItem(countryFlagIv, 300)
//
//        //code
//        countryCodeTv.text = "+${code.removePrefix("+")}"
//        animateItem(countryCodeTv, 500)
//    }

//    fun onCountryClicked(action: () -> Unit) {
//        countryPicker.clickWithThrottle(1000) {
//            action.invoke()
//        }
//    }

//    fun getCountryCode(): String = countryCodeTv.text.removePrefix("+").toString()

//    fun onEditTextClicked(onClicked: () -> Unit) {
//        editText.isFocusableInTouchMode = false
//        editText.isLongClickable = false
//        editText.setOnClickListener {
//            onClicked.invoke()
//        }
//    }

//    fun setError(value: String) {
//        if (value.isNotEmpty()) {
//            underlineView.backgroundTintList =
//                ContextCompat.getColorStateList(context, R.color.red_5)
//            errorText.visibility = View.VISIBLE
//            errorText.text = value
//        } else {
//            underlineView.backgroundTintList =
//                ContextCompat.getColorStateList(context, R.color.blue_6)
//            errorText.visibility = View.GONE
//        }
//    }

    fun onClicked(onClicked: () -> Unit) {
        container.setOnClickListener {
            onClicked.invoke()
        }
    }

    private fun animateItem(view: View, duration: Long) {
        view.translationX = -20f
        view.alpha = 0f
        view.visibility = View.VISIBLE
        view.animate().translationX(0f).duration = duration - 100
        view.animate().alpha(1f).duration = duration
    }

    private fun setUp(attrs: AttributeSet? = null, defStyleAttr: Int? = null) {
        val root = LayoutInflater.from(context).inflate(R.layout.lt_edit_text, this, true)

        container = root.findViewById(R.id.container)
        editText = root.findViewById(R.id.main_editText)
//        errorText = root.findViewById(R.id.error_tv)
//        countryCodeTv = root.findViewById(R.id.country_code_tv)
//        countryFlagIv = root.findViewById(R.id.country_flag_iv)
//        countryPicker = root.findViewById(R.id.country_picker)
//        underlineView = root.findViewById(R.id.underline)
//        divider = root.findViewById(R.id.divider)
//        progressBar = root.findViewById(R.id.progress_bar)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.LTEditText)
            //text
            text = typedArray.getText(R.styleable.LTEditText_lt_et_text)?.toString() ?: ""
            textColor = typedArray.getColor(
                R.styleable.LTEditText_lt_et_textColor,
                ContextCompat.getColor(context, DEFAULT_TEXT_COLOR)
            )
            //hint
            hint = typedArray.getText(R.styleable.LTEditText_lt_et_hint)?.toString() ?: ""
            hintColor = typedArray.getColor(
                R.styleable.LTEditText_lt_et_hintColor,
                context.getResourceColor(DEFAULT_HINT_COLOR)
            )

            edTextHeight = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_height,
                resources.getDimensionPixelSize(R.dimen.edit_text_height)
            )

//            etInputType = typedArray.getInt(
//                R.styleable.MainUnderlinedEditText_uetInputType,
//                DEFAULT_TEXT_INPUT_TYPE
//            )
//
//            etTextAlignment =
//                typedArray.getInt(
//                    R.styleable.MainUnderlinedEditText_uetTextAlignment,
//                    DEFAULT_TEXT_ALIGNMENT
//                )
//
//            etGravity = typedArray.getInt(
//                R.styleable.MainUnderlinedEditText_uetGravity,
//                DEFAULT_TEXT_GRAVITY
//            )
//
//            fontFamilyId = typedArray.getResourceId(
//                R.styleable.MainUnderlinedEditText_uetFontFamily,
//                R.font.omnes_regular
//            )
//            mainEditText.typeface = ResourcesCompat.getFont(context, fontFamilyId);
//
//            maxLength =
//                typedArray.getInt(R.styleable.MainUnderlinedEditText_uetMaxLength, Int.MAX_VALUE)
//
//            mainLayoutDirection =
//                typedArray.getInt(R.styleable.MainUnderlinedEditText_uetLayoutDirection, 3)
//
//            enableCountryPicker = typedArray.getBoolean(
//                R.styleable.MainUnderlinedEditText_uet_enableCountryPicker,
//                DEFAULT_ENABLE_COUNTRY_PICKER
//            )

            typedArray.recycle()

        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable {
        val parcel = super.onSaveInstanceState()
        return LtEditTextState(
            parcel = parcel,
            text = text,
            textColor = textColor,
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is LtEditTextState) {
            this.text = state.text
            this.textColor = state.textColor
        }
        super.onRestoreInstanceState(state)
    }
}