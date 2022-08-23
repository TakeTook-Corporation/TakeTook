package com.example.taketook2.ui.profile.pin;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.taketook2.R;

public class PinEntryEditText extends AppCompatEditText {

    private static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    private static final String DEFAULT_MASK = "\u25CF";

    protected String mask = null;
    protected StringBuilder maskChars = null;
    protected String singleCharHint = null;
    protected int animatedType = 0;
    protected float space = 24; // 24 dp by default, space between the lines
    protected float charSize;
    protected float numChars = 4;
    protected float textBottomPadding = 8; //8dp by default, height of the text from our lines
    protected int maxLength = 4;
    protected RectF[] lineCoords;
    protected float[] charBottom;
    protected Paint charPaint;
    protected Paint lastCharPaint;
    protected Paint singleCharPaint;
    protected Drawable pinBackground;
    protected int xB;
    protected int x;
    protected Rect textHeight = new Rect();
    protected boolean isDigitSquare = false;

    protected OnClickListener clickListener;
    protected PinEntryEditText.OnPinEnteredListener onPinEnteredListener = null;

    protected float lineStroke = 1; //1dp by default
    protected float lineStrokeSelected = 2; //2dp by default
    protected Paint linesPaint;
    protected boolean animate = false;
    protected boolean hasError = false;
    protected ColorStateList originalTextColors;
    protected int[][] states = new int[][]{
            new int[]{android.R.attr.state_selected}, // selected
            new int[]{android.R.attr.state_active}, // error
            new int[]{android.R.attr.state_focused}, // focused
            new int[]{-android.R.attr.state_focused}, // unfocused
    };

    protected int[] colors = new int[]{
            Color.GREEN,
            Color.RED,
            Color.BLACK,
            Color.GRAY
    };

    protected ColorStateList colorStates = new ColorStateList(states, colors);

    public PinEntryEditText(Context context) {
        super(context);
    }

    public PinEntryEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PinEntryEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
        numChars = maxLength;

        setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(maxLength)
        });

        setText(null);
        invalidate();
    }

    public void setMask(String mask) {
        this.mask = mask;
        maskChars = null;
        invalidate();
    }

    public void setSingleCharHint(String hint) {
        singleCharHint = hint;
        invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        float multi = context.getResources().getDisplayMetrics().density;
        lineStroke = multi * lineStroke;
        lineStrokeSelected = multi * lineStrokeSelected;
        space = multi * space; //convert to pixels for our density
        textBottomPadding = multi * textBottomPadding; //convert to pixels for our density

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PinEntryEditText, 0, 0);
        try {
            TypedValue outValue = new TypedValue();
            ta.getValue(R.styleable.PinEntryEditText_pinAnimationType, outValue);
            animatedType = outValue.data;
            mask = ta.getString(R.styleable.PinEntryEditText_pinCharacterMask);
            singleCharHint = ta.getString(R.styleable.PinEntryEditText_pinRepeatedHint);
            lineStroke = ta.getDimension(R.styleable.PinEntryEditText_pinLineStroke, lineStroke);
            lineStrokeSelected = ta.getDimension(R.styleable.PinEntryEditText_pinLineStrokeSelected, lineStrokeSelected);
            space = ta.getDimension(R.styleable.PinEntryEditText_pinCharacterSpacing, space);
            textBottomPadding = ta.getDimension(R.styleable.PinEntryEditText_pinTextBottomPadding, textBottomPadding);
            isDigitSquare = ta.getBoolean(R.styleable.PinEntryEditText_pinBackgroundIsSquare, isDigitSquare);
            pinBackground = ta.getDrawable(R.styleable.PinEntryEditText_pinBackgroundDrawable);
            ColorStateList colors = ta.getColorStateList(R.styleable.PinEntryEditText_pinLineColors);
            if (colors != null) {
                colorStates = colors;
            }
        } finally {
            ta.recycle();
        }

        charPaint = new Paint(getPaint());
        lastCharPaint = new Paint(getPaint());
        singleCharPaint = new Paint(getPaint());
        linesPaint = new Paint(getPaint());
        linesPaint.setStrokeWidth(lineStroke);

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.color.blue,
                outValue, true);
        int colorSelected = outValue.data;
        colors[0] = colorSelected;

        int colorFocused = isInEditMode() ? Color.GRAY : ContextCompat.getColor(context, R.color.pin_normal);
        colors[1] = colorFocused;

        int colorUnfocused = isInEditMode() ? Color.GRAY : ContextCompat.getColor(context, R.color.pin_normal);
        colors[2] = colorUnfocused;

        setBackgroundResource(0);

        maxLength = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 4);
        numChars = maxLength;


        //Disable copy paste
        super.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        // When tapped, move cursor to end of text.


        //If input type is password and no mask is set, use a default mask
        if ((getInputType() & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD && TextUtils.isEmpty(mask)) {
            mask = DEFAULT_MASK;
        } else if ((getInputType() & InputType.TYPE_NUMBER_VARIATION_PASSWORD) == InputType.TYPE_NUMBER_VARIATION_PASSWORD && TextUtils.isEmpty(mask)) {
            mask = DEFAULT_MASK;
        }

        if (!TextUtils.isEmpty(mask)) {
            maskChars = getMaskChars();
        }

        //Height of the characters, used if there is a background drawable
        getPaint().getTextBounds("|", 0, 1, textHeight);

        animate = animatedType > -1;
    }

    @Override
    public void setInputType(int type) {
        super.setInputType(type);

        if ((type & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD
                || (type & InputType.TYPE_NUMBER_VARIATION_PASSWORD) == InputType.TYPE_NUMBER_VARIATION_PASSWORD) {
            // If input type is password and no mask is set, use a default mask
            if (TextUtils.isEmpty(mask)) {
                setMask(DEFAULT_MASK);
            }
        } else {
            // If input type is not password, remove mask
            setMask(null);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalTextColors = getTextColors();
        if (originalTextColors != null) {
            lastCharPaint.setColor(originalTextColors.getDefaultColor());
            charPaint.setColor(originalTextColors.getDefaultColor());
            singleCharPaint.setColor(getCurrentHintTextColor());
        }
        int availableWidth = getWidth() - ViewCompat.getPaddingEnd(this) - ViewCompat.getPaddingStart(this);
        if (space < 0) {
            charSize = (availableWidth / (numChars * 2 - 1));
        } else {
            charSize = (availableWidth - (space * (numChars - 1))) / numChars;
        }
        lineCoords = new RectF[(int) numChars];
        charBottom = new float[(int) numChars];
        int startX;
        int bottom = getHeight() - getPaddingBottom();
        int rtlFlag;
        final boolean isLayoutRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (isLayoutRtl) {
            rtlFlag = -1;
            startX = (int) (getWidth() - ViewCompat.getPaddingStart(this) - charSize);
        } else {
            rtlFlag = 1;
            startX = ViewCompat.getPaddingStart(this);
        }
        for (int i = 0; i < numChars; i++) {
            lineCoords[i] = new RectF(startX, bottom, startX + charSize, bottom);
            if (pinBackground != null) {
                if (isDigitSquare) {
                    lineCoords[i].top = getPaddingTop();
                    lineCoords[i].right = startX + lineCoords[i].width();
                } else {
                    lineCoords[i].top -= textHeight.height() + textBottomPadding * 2;
                }
            }

            if (space < 0) {
                startX += rtlFlag * charSize * 2;
            } else {
                startX += rtlFlag * (charSize + space);
            }
            charBottom[i] = lineCoords[i].bottom - textBottomPadding;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isDigitSquare) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int measuredWidth = 0;
            int measuredHeight = 0;
            // If we want a square or circle pin box, we might be able
            // to figure out the dimensions outselves
            // if width and height are set to wrap_content or match_parent
            if (widthMode == MeasureSpec.EXACTLY) {
                measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
                measuredHeight = (int) ((measuredWidth - (numChars - 1 * space)) / numChars);
            } else if (heightMode == MeasureSpec.EXACTLY) {
                measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
                measuredWidth = (int) ((measuredHeight * numChars) + (space * numChars - 1));
            } else if (widthMode == MeasureSpec.AT_MOST) {
                measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
                measuredHeight = (int) ((measuredWidth - (numChars - 1 * space)) / numChars);
            } else if (heightMode == MeasureSpec.AT_MOST) {
                measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
                measuredWidth = (int) ((measuredHeight * numChars) + (space * numChars - 1));
            } else {
                // Both unspecific
                // Try for a width based on our minimum
                measuredWidth = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();

                // Whatever the width ends up being, ask for a height that would let the pie
                // get as big as it can
                measuredHeight = (int) ((measuredWidth - (numChars - 1 * space)) / numChars);
            }

            setMeasuredDimension(
                    resolveSizeAndState(measuredWidth, widthMeasureSpec, 1), resolveSizeAndState(measuredHeight, heightMeasureSpec, 0));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        clickListener = l;
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        throw new RuntimeException("setCustomSelectionActionModeCallback() not supported.");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        CharSequence text = getFullText();
        int textLength = text.length();
        float[] textWidths = new float[textLength];
        getPaint().getTextWidths(text, 0, textLength, textWidths);

        float hintWidth = 0;
        if (singleCharHint != null) {
            float[] hintWidths = new float[singleCharHint.length()];
            getPaint().getTextWidths(singleCharHint, hintWidths);
            for (float i : hintWidths) {
                hintWidth += i;
            }
        }
        for (int i = 0; i < numChars; i++) {
            //If a background for the pin characters is specified, it should be behind the characters.
            if (pinBackground != null) {
                updateDrawableState(i < textLength, i == textLength);
                pinBackground.setBounds((int) lineCoords[i].left, (int) lineCoords[i].top, (int) lineCoords[i].right, (int) lineCoords[i].bottom);
                pinBackground.draw(canvas);
            }
            float middle = lineCoords[i].left + charSize / 2;
            if (textLength > i) {
                if (!animate || i != textLength - 1) {
                    canvas.drawText(text, i, i + 1, middle - textWidths[i] / 2, charBottom[i], charPaint);
                } else {
                    canvas.drawText(text, i, i + 1, middle - textWidths[i] / 2, charBottom[i], lastCharPaint);
                }
            } else if (singleCharHint != null) {
                canvas.drawText(singleCharHint, middle - hintWidth / 2, charBottom[i], singleCharPaint);
            }
            //The lines should be in front of the text (because that's how I want it).
            if (pinBackground == null & i >= textLength) {
                linesPaint.setColor(getResources().getColor(R.color.black)); //TODO primary
                canvas.drawCircle((lineCoords[i].left + lineCoords[i].right) / 2, getHeight() / 2, getResources().getDimensionPixelSize(R.dimen.g), linesPaint);
                //canvas.drawOval(mLineCoords[i].left, mLineCoords[i].top, mLineCoords[i].right, mLineCoords[i].bottom, mLinesPaint);
            }
        }
    }


    private CharSequence getFullText() {
        if (TextUtils.isEmpty(mask)) {
            return getText();
        } else {
            return getMaskChars();
        }
    }

    private StringBuilder getMaskChars() {
        if (maskChars == null) {
            maskChars = new StringBuilder();
        }
        int textLength = getText().length();
        while (maskChars.length() != textLength) {
            if (maskChars.length() < textLength) {
                maskChars.append(mask);
            } else {
                maskChars.deleteCharAt(maskChars.length() - 1);
            }
        }
        return maskChars;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }


    protected void updateDrawableState(boolean hasText, boolean isNext) {
        if (hasError) {
            pinBackground.setState(new int[]{android.R.attr.state_active});
        } else if (isFocused()) {
            pinBackground.setState(new int[]{android.R.attr.state_focused});
            if (isNext) {
                pinBackground.setState(new int[]{android.R.attr.state_focused, android.R.attr.state_selected});
            } else if (hasText) {
                pinBackground.setState(new int[]{android.R.attr.state_focused, android.R.attr.state_checked});
            }
        } else {
            if (hasText) {
                pinBackground.setState(new int[]{-android.R.attr.state_focused, android.R.attr.state_checked});
            } else {
                pinBackground.setState(new int[]{-android.R.attr.state_focused});
            }
        }
    }

    public void setError(boolean hasError) {
        this.hasError = hasError;
        invalidate();
    }

    public boolean isError() {
        return hasError;
    }

    /**
     * Request focus on this PinEntryEditText
     */
    public void focus() {
        requestFocus();

        // Show keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(this, 0);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf) {
        super.setTypeface(tf);
        setCustomTypeface(tf);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf, int style) {
        super.setTypeface(tf, style);
        setCustomTypeface(tf);
    }

    private void setCustomTypeface(@Nullable Typeface tf) {
        if (charPaint != null) {
            charPaint.setTypeface(tf);
            lastCharPaint.setTypeface(tf);
            singleCharPaint.setTypeface(tf);
            linesPaint.setTypeface(tf);
        }
    }

    public void setPinLineColors(ColorStateList colors) {
        colorStates = colors;
        invalidate();
    }

    public void setPinBackground(Drawable pinBackground) {
        this.pinBackground = pinBackground;
        invalidate();
    }


    public void setXb(int k) {
        xB = k;
        invalidate();
    }

    public void setX(int k) {
        x = k;
        invalidate();
    }


    @Override
    protected void onTextChanged(CharSequence text, final int start, int lengthBefore, final int lengthAfter) {
        setError(false);
        if (lineCoords == null || !animate) {
            if (onPinEnteredListener != null && text.length() == maxLength) {
                onPinEnteredListener.onPinEntered(text);
            }
            return;
        }

        if (animatedType == -1) {
            invalidate();
            return;
        }

        if (lengthAfter > lengthBefore) {
            if (animatedType == 0) {
                animatePopIn();
            } else {
                animateBottomUp(text, start);
            }
        }
    }

    private void animatePopIn() {
        ValueAnimator va = ValueAnimator.ofFloat(1, getPaint().getTextSize());
        va.setDuration(200);
        va.setInterpolator(new OvershootInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lastCharPaint.setTextSize((Float) animation.getAnimatedValue());
                PinEntryEditText.this.invalidate();
            }
        });
        if (getText().length() == maxLength && onPinEnteredListener != null) {
            va.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    onPinEnteredListener.onPinEntered(getText());
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        va.start();
    }

    private void animateBottomUp(CharSequence text, final int start) {
        charBottom[start] = lineCoords[start].bottom - textBottomPadding;
        ValueAnimator animUp = ValueAnimator.ofFloat(charBottom[start] + getPaint().getTextSize(), charBottom[start]);
        animUp.setDuration(300);
        animUp.setInterpolator(new OvershootInterpolator());
        animUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                charBottom[start] = value;
                PinEntryEditText.this.invalidate();
            }
        });

        lastCharPaint.setAlpha(255);
        ValueAnimator animAlpha = ValueAnimator.ofInt(0, 255);
        animAlpha.setDuration(300);
        animAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                lastCharPaint.setAlpha(value);
            }
        });

        AnimatorSet set = new AnimatorSet();
        if (text.length() == maxLength && onPinEnteredListener != null) {
            set.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    onPinEnteredListener.onPinEntered(getText());
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        set.playTogether(animUp, animAlpha);
        set.start();
    }

    public void setAnimateText(boolean animate) {
        this.animate = animate;
    }

    public void setOnPinEnteredListener(PinEntryEditText.OnPinEnteredListener l) {
        onPinEnteredListener = l;
    }

    public interface OnPinEnteredListener {
        void onPinEntered(CharSequence str);
    }
}
