package com.example.taketook2.ui.profile.pin

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.animation.OvershootInterpolator
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.example.taketook2.R

class PinEntryEditText : AppCompatEditText {
    private var mask: String? = null
        set(value) {
            field = value
            maskChars = null
            invalidate()
        }
    private var maskChars: StringBuilder? = null
    private var singleCharHint: String? = null
    private var animatedType = 0
    private var space = 24f // 24 dp by default, space between the lines
    private var charSize = 0f
    private var numChars = 4f
    private var textBottomPadding = 8f //8dp by default, height of the text from our lines
    private var maxLength = 4
    private var lineCoords: Array<RectF?>? = arrayOf()
    private var charBottom: FloatArray = floatArrayOf()
    private var charPaint: Paint? = null
    private var lastCharPaint: Paint? = null
    private var singleCharPaint: Paint? = null
    private var pinBackground: Drawable? = null
    private var textHeight = Rect()
    private var isDigitSquare = false
    private var clickListener: OnClickListener? = null
    private var onPinEnteredListener: OnPinEnteredListener? = null
    private var lineStroke = 1f //1dp by default
    private var lineStrokeSelected = 2f //2dp by default
    private var linesPaint: Paint? = null
    private var animate = false
    private var hasError = false
    private var originalTextColors: ColorStateList? = null
    private var states = arrayOf(intArrayOf(android.R.attr.state_selected),
        intArrayOf(android.R.attr.state_active),
        intArrayOf(android.R.attr.state_focused),
        intArrayOf(-android.R.attr.state_focused))
    private var colors = intArrayOf(
        Color.GREEN,
        Color.RED,
        Color.BLACK,
        Color.GRAY
    )
    private var colorStates = ColorStateList(states, colors)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val multi = context.resources.displayMetrics.density
        lineStroke *= multi
        lineStrokeSelected *= multi
        space *= multi //convert to pixels for our density
        textBottomPadding *= multi //convert to pixels for our density
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PinEntryEditText, 0, 0)
        try {
            val outValue = TypedValue()
            ta.getValue(R.styleable.PinEntryEditText_pinAnimationType, outValue)
            animatedType = outValue.data
            mask = ta.getString(R.styleable.PinEntryEditText_pinCharacterMask)
            singleCharHint = ta.getString(R.styleable.PinEntryEditText_pinRepeatedHint)
            lineStroke = ta.getDimension(R.styleable.PinEntryEditText_pinLineStroke, lineStroke)
            lineStrokeSelected = ta.getDimension(R.styleable.PinEntryEditText_pinLineStrokeSelected, lineStrokeSelected)
            space = ta.getDimension(R.styleable.PinEntryEditText_pinCharacterSpacing, space)
            textBottomPadding = ta.getDimension(R.styleable.PinEntryEditText_pinTextBottomPadding, textBottomPadding)
            isDigitSquare = ta.getBoolean(R.styleable.PinEntryEditText_pinBackgroundIsSquare, isDigitSquare)
            pinBackground = ta.getDrawable(R.styleable.PinEntryEditText_pinBackgroundDrawable)
            val colors = ta.getColorStateList(R.styleable.PinEntryEditText_pinLineColors)
            if (colors != null) {
                colorStates = colors
            }
        } finally {
            ta.recycle()
        }
        charPaint = Paint(paint)
        lastCharPaint = Paint(paint)
        singleCharPaint = Paint(paint)
        linesPaint = Paint(paint)
        linesPaint!!.strokeWidth = lineStroke
        val outValue = TypedValue()
        context.theme.resolveAttribute(R.color.blue,
            outValue, true)
        val colorSelected = outValue.data
        colors[0] = colorSelected
        val colorFocused = if (isInEditMode) Color.GRAY else ContextCompat.getColor(context, R.color.pin_normal)
        colors[1] = colorFocused
        val colorUnfocused = if (isInEditMode) Color.GRAY else ContextCompat.getColor(context, R.color.pin_normal)
        colors[2] = colorUnfocused
        setBackgroundResource(0)
        maxLength = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 4)
        numChars = maxLength.toFloat()


        //Disable copy paste
        super.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        })
        // When tapped, move cursor to end of text.


        //If input type is password and no mask is set, use a default mask
        if (inputType and InputType.TYPE_TEXT_VARIATION_PASSWORD == InputType.TYPE_TEXT_VARIATION_PASSWORD && TextUtils.isEmpty(mask)) {
            mask = DEFAULT_MASK
        } else if (inputType and InputType.TYPE_NUMBER_VARIATION_PASSWORD == InputType.TYPE_NUMBER_VARIATION_PASSWORD && TextUtils.isEmpty(mask)) {
            mask = DEFAULT_MASK
        }
        if (!TextUtils.isEmpty(mask)) {
            maskChars = getUpdatedMaskChars()
        }

        //Height of the characters, used if there is a background drawable
        paint.getTextBounds("|", 0, 1, textHeight)
        animate = animatedType > -1
    }

    override fun setInputType(type: Int) {
        super.setInputType(type)
        if (type and InputType.TYPE_TEXT_VARIATION_PASSWORD == InputType.TYPE_TEXT_VARIATION_PASSWORD
            || type and InputType.TYPE_NUMBER_VARIATION_PASSWORD == InputType.TYPE_NUMBER_VARIATION_PASSWORD
        ) {
            // If input type is password and no mask is set, use a default mask
            if (TextUtils.isEmpty(mask)) {
                mask = DEFAULT_MASK
            }
        } else {
            // If input type is not password, remove mask
            mask = null
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalTextColors = textColors
        if (originalTextColors != null) {
            lastCharPaint!!.color = originalTextColors!!.defaultColor
            charPaint!!.color = originalTextColors!!.defaultColor
            singleCharPaint!!.color = currentHintTextColor
        }
        val availableWidth = width - ViewCompat.getPaddingEnd(this) - ViewCompat.getPaddingStart(this)
        charSize = if (space < 0) {
            availableWidth / (numChars * 2 - 1)
        } else {
            (availableWidth - space * (numChars - 1)) / numChars
        }
        lineCoords = arrayOfNulls(numChars.toInt())
        charBottom = FloatArray(numChars.toInt())
        var startX: Int
        val bottom = height - paddingBottom
        val rtlFlag: Int
        val isLayoutRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
        if (isLayoutRtl) {
            rtlFlag = -1
            startX = (width - ViewCompat.getPaddingStart(this) - charSize).toInt()
        } else {
            rtlFlag = 1
            startX = ViewCompat.getPaddingStart(this)
        }
        var i = 0
        while (i < numChars) {
            lineCoords!![i] = RectF(startX.toFloat(), bottom.toFloat(), startX + charSize, bottom.toFloat())
            if (pinBackground != null) {
                if (isDigitSquare) {
                    lineCoords!![i]!!.top = paddingTop.toFloat()
                    lineCoords!![i]!!.right = startX + lineCoords!![i]!!.width()
                } else {
                    lineCoords!![i]!!.top -= textHeight.height() + textBottomPadding * 2
                }
            }
            startX += if (space < 0) {
                (rtlFlag * charSize * 2).toInt()
            } else {
                (rtlFlag * (charSize + space)).toInt()
            }
            charBottom[i] = lineCoords!![i]!!.bottom - textBottomPadding
            i++
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isDigitSquare) {
            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            val measuredWidth: Int
            val measuredHeight: Int
            // If we want a square or circle pin box, we might be able
            // to figure out the dimensions outselves
            // if width and height are set to wrap_content or match_parent
            if (widthMode == MeasureSpec.EXACTLY) {
                measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
                measuredHeight = ((measuredWidth - (numChars - 1 * space)) / numChars).toInt()
            } else if (heightMode == MeasureSpec.EXACTLY) {
                measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
                measuredWidth = (measuredHeight * numChars + (space * numChars - 1)).toInt()
            } else if (widthMode == MeasureSpec.AT_MOST) {
                measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
                measuredHeight = ((measuredWidth - (numChars - 1 * space)) / numChars).toInt()
            } else if (heightMode == MeasureSpec.AT_MOST) {
                measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
                measuredWidth = (measuredHeight * numChars + (space * numChars - 1)).toInt()
            } else {
                // Both unspecific
                // Try for a width based on our minimum
                measuredWidth = paddingLeft + paddingRight + suggestedMinimumWidth

                // Whatever the width ends up being, ask for a height that would let the pie
                // get as big as it can
                measuredHeight = ((measuredWidth - (numChars - 1 * space)) / numChars).toInt()
            }
            setMeasuredDimension(
                resolveSizeAndState(measuredWidth, widthMeasureSpec, 1), resolveSizeAndState(measuredHeight, heightMeasureSpec, 0))
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        clickListener = listener
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback?) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas);
        val text = fullText
        val textLength = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(text, 0, textLength, textWidths)
        var hintWidth = 0f
        if (singleCharHint != null) {
            val hintWidths = FloatArray(singleCharHint!!.length)
            paint.getTextWidths(singleCharHint, hintWidths)
            for (i in hintWidths) {
                hintWidth += i
            }
        }
        var i = 0
        while (i < numChars) {
            if (pinBackground != null) {
                updateDrawableState(i < textLength, i == textLength)
                pinBackground!!.setBounds(lineCoords!![i]!!.left.toInt(),
                    lineCoords!![i]!!.top.toInt(),
                    lineCoords!![i]!!.right.toInt(),
                    lineCoords!![i]!!.bottom.toInt())
                pinBackground!!.draw(canvas)
            }
            val middle = lineCoords!![i]!!.left + charSize / 2
            if (textLength > i) {
                if (!animate || i != textLength - 1) {
                    canvas.drawText(text, i, i + 1, middle - textWidths[i] / 2, charBottom[i], charPaint!!)
                } else {
                    canvas.drawText(text, i, i + 1, middle - textWidths[i] / 2, charBottom[i], lastCharPaint!!)
                }
            } else if (singleCharHint != null) {
                canvas.drawText(singleCharHint!!, middle - hintWidth / 2, charBottom[i], singleCharPaint!!)
            }
            if ((pinBackground == null) and (i >= textLength)) {
                linesPaint!!.color = resources.getColor(R.color.black) //TODO primary
                canvas.drawCircle((lineCoords!![i]!!.left + lineCoords!![i]!!.right) / 2,
                    (height / 2).toFloat(),
                    resources.getDimensionPixelSize(R.dimen.g).toFloat(),
                    linesPaint!!)
            }
            i++
        }
    }

    private val fullText: CharSequence?
        get() = if (TextUtils.isEmpty(mask)) {
            text
        } else {
            getUpdatedMaskChars()
        }

    private fun getUpdatedMaskChars(): StringBuilder? {
        if (maskChars == null) {
            maskChars = StringBuilder()
        }
        val textLength = text!!.length
        while (maskChars!!.length != textLength) {
            if (maskChars!!.length < textLength) {
                maskChars!!.append(mask)
            } else {
                maskChars!!.deleteCharAt(maskChars!!.length - 1)
            }
        }
        return maskChars
    }

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(text, type)
    }

    private fun updateDrawableState(hasText: Boolean, isNext: Boolean) {
        if (hasError) {
            pinBackground!!.state = intArrayOf(android.R.attr.state_active)
        } else if (isFocused) {
            pinBackground!!.state = intArrayOf(android.R.attr.state_focused)
            if (isNext) {
                pinBackground!!.state = intArrayOf(android.R.attr.state_focused, android.R.attr.state_selected)
            } else if (hasText) {
                pinBackground!!.state = intArrayOf(android.R.attr.state_focused, android.R.attr.state_checked)
            }
        } else {
            if (hasText) {
                pinBackground!!.state = intArrayOf(-android.R.attr.state_focused, android.R.attr.state_checked)
            } else {
                pinBackground!!.state = intArrayOf(-android.R.attr.state_focused)
            }
        }
    }

    private fun setError(hasError: Boolean) {
        this.hasError = hasError
        invalidate()
    }

    override fun setTypeface(tf: Typeface?) {
        super.setTypeface(tf)
        setCustomTypeface(tf)
    }

    override fun setTypeface(tf: Typeface?, style: Int) {
        super.setTypeface(tf, style)
        setCustomTypeface(tf)
    }

    private fun setCustomTypeface(tf: Typeface?) {
        if (charPaint != null) {
            charPaint!!.typeface = tf
            lastCharPaint!!.typeface = tf
            singleCharPaint!!.typeface = tf
            linesPaint!!.typeface = tf
        }
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        setError(false)
        if (lineCoords == null || !animate) {
            if (onPinEnteredListener != null && text.length == maxLength) {
                onPinEnteredListener!!.onPinEntered(text)
            }
            return
        }
        if (animatedType == -1) {
            invalidate()
            return
        }
        if (lengthAfter > lengthBefore) {
            if (animatedType == 0) {
                animatePopIn()
            } else {
                animateBottomUp(text, start)
            }
        }
    }

    private fun animatePopIn() {
        val va = ValueAnimator.ofFloat(1f, paint.textSize)
        va.duration = 200
        va.interpolator = OvershootInterpolator()
        va.addUpdateListener { animation ->
            lastCharPaint!!.textSize = (animation.animatedValue as Float)
            this@PinEntryEditText.invalidate()
        }
        if (text!!.length == maxLength && onPinEnteredListener != null) {
            va.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    onPinEnteredListener!!.onPinEntered(text)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        va.start()
    }

    private fun animateBottomUp(text: CharSequence, start: Int) {
        charBottom[start] = lineCoords!![start]!!.bottom - textBottomPadding
        val animUp = ValueAnimator.ofFloat(charBottom[start] + paint.textSize, charBottom[start])
        animUp.duration = 300
        animUp.interpolator = OvershootInterpolator()
        animUp.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            charBottom[start] = value
            this@PinEntryEditText.invalidate()
        }
        lastCharPaint!!.alpha = 255
        val animAlpha = ValueAnimator.ofInt(0, 255)
        animAlpha.duration = 300
        animAlpha.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            lastCharPaint!!.alpha = value
        }
        val set = AnimatorSet()
        if (text.length == maxLength && onPinEnteredListener != null) {
            set.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    onPinEnteredListener!!.onPinEntered(getText())
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        set.playTogether(animUp, animAlpha)
        set.start()
    }

    interface OnPinEnteredListener {
        fun onPinEntered(str: CharSequence?)
    }

    companion object {
        private const val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
        private const val DEFAULT_MASK = "\u25CF"
    }
}