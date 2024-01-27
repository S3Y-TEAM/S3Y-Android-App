package com.graduation.presentation.components.editText

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
import com.graduation.core.extensions.getResourceColor
import com.graduation.core.utils.tryNow
import com.graduation.presentation.R
import com.google.android.material.card.MaterialCardView

class LTEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var container: MaterialCardView
    private lateinit var editText: EditText

    /**
     * Set your EditText preferences here and it will be applied global :)
     */
    companion object {
        //height
        val CONTAINER_HEIGHT = R.dimen.ltet_height

        //text
        val DEFAULT_TEXT_COLOR = R.color.blue_6
        val DEFAULT_HINT_COLOR = R.color.gray_2
        val DEFAULT_ERROR_COLOR = R.color.red_5

        //container
        val CONTAINER_STROKE_WIDTH = R.dimen.ltet_stroke_width
        val CONTAINER_STROKE_COLOR = R.color.gray_2
        val CONTAINER_BACKGROUND_COLOR = R.color.blue_3
        val CONTAINER_RADIUS = R.dimen.ltet_radius

        //padding
        val CONTAINER_PADDING_START = 0
        val CONTAINER_PADDING_END = 0
        val CONTAINER_PADDING_TOP = 0
        val CONTAINER_PADDING_BOTTOM = 0

        // const val DEFAULT_TEXT_INPUT_TYPE = 0x00000001

//        const val DEFAULT_TEXT_ALIGNMENT = 5 // viewStart
//        const val DEFAULT_TEXT_GRAVITY = Gravity.CENTER
//        const val DEFAULT_ENABLE_COUNTRY_PICKER = false
    }

    var containerHeight: Int = resources.getDimensionPixelSize(CONTAINER_HEIGHT)
        set(value) {
            field = value
            container.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value)
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

    var hintColor: Int = DEFAULT_HINT_COLOR
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

    var errorTextColor: Int = DEFAULT_ERROR_COLOR
        set(value) {
            field = value
            editText.setHintTextColor(value)
        }

    /** Container **/
    var containerStrokeWidth: Int = CONTAINER_STROKE_WIDTH
        set(value) {
            field = value
            tryNow {
                container.strokeWidth = resources.getDimension(value).toInt()
            }
        }

    var containerStrokeColor: Int = CONTAINER_STROKE_COLOR
        set(value) {
            field = value
            tryNow {
                container.strokeColor = ContextCompat.getColor(context, value)
            }
        }

    var containerBackgroundColor: Int = CONTAINER_BACKGROUND_COLOR
        set(value) {
            field = value
            container.setCardBackgroundColor(value)
        }

    var containerRadius: Int = CONTAINER_RADIUS
        set(value) {
            field = value
            //container.radius = resources.getDimension(value)
        }

    /** Padding **/
    var containerPaddingStart: Int = 0
        set(value) {
            field = value

            editText.setPadding(
                value, editText.paddingTop, editText.paddingRight, editText.paddingBottom
            )
        }

    var containerPaddingEnd: Int = 0
        set(value) {
            field = value
            editText.setPadding(
                editText.paddingStart, editText.paddingTop, value, editText.paddingBottom
            )
        }

    var containerPaddingTop: Int = 0
        set(value) {
            field = value
            editText.setPadding(
                editText.paddingStart, value, editText.paddingRight, editText.paddingBottom
            )
        }

    var containerPaddingBottom: Int = 0
        set(value) {
            field = value
            editText.setPadding(
                editText.paddingStart, editText.paddingTop, editText.paddingRight, value
            )
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
                R.styleable.LTEditText_lt_et_hintColor, context.getResourceColor(DEFAULT_HINT_COLOR)
            )
            //container
            containerStrokeColor = typedArray.getColor(
                R.styleable.LTEditText_lt_et_containerStrokeColor,
                ContextCompat.getColor(context, CONTAINER_STROKE_COLOR)
            )
            containerStrokeWidth = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_containerStrokeWidth, CONTAINER_STROKE_WIDTH
            )
            containerBackgroundColor = typedArray.getColor(
                R.styleable.LTEditText_lt_et_containerBgColor,
                ContextCompat.getColor(context, CONTAINER_BACKGROUND_COLOR)
            )
            containerRadius = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_containerRadius,
                resources.getDimension(CONTAINER_RADIUS).toInt()
            )
            //padding
            containerPaddingStart = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_containerPaddingStart, 0
            )
            containerPaddingEnd = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_containerPaddingEnd, 0
            )
            containerPaddingBottom = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_containerPaddingBottom, 0
            )
            containerPaddingTop = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_containerPaddingTop, 0
            )

            containerHeight = typedArray.getDimensionPixelSize(
                R.styleable.LTEditText_lt_et_containerHeight,
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