package com.example.core.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/*
 * @author y.gladkikh
 */
class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttrs) {

    init {
        clipToOutline = true
        setBackgroundResource(R.drawable.circle)
        scaleType = ScaleType.CENTER_CROP
    }
}
