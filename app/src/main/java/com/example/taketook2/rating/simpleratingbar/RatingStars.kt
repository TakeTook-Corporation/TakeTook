package com.example.taketook2.rating.simpleratingbar

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.taketook2.R

class RatingStars @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private var totalStars = 4
    private var mRating = -1

    private var animationType = 0
    private var emptyDrawable: Drawable? = null
    private var filledDrawable: Drawable? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingStars)
        initParamsValue(typedArray)
    }

    private fun initParamsValue(typedArray: TypedArray) {
        totalStars = typedArray.getInteger(R.styleable.RatingStars_total_stars, 4)
        totalStars -= 1
        mRating = typedArray.getInteger(R.styleable.RatingStars_rating, -1)
        emptyDrawable = if (typedArray.hasValue(R.styleable.RatingStars_drawable_empty)) ContextCompat.getDrawable(context,
            typedArray.getResourceId(R.styleable.RatingStars_drawable_empty, View.NO_ID))
        else ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_24dp)
        filledDrawable = if (typedArray.hasValue(R.styleable.RatingStars_drawable_filled)) ContextCompat.getDrawable(context,
            typedArray.getResourceId(R.styleable.RatingStars_drawable_filled, View.NO_ID))
        else ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp)
        animationType = typedArray.getInteger(R.styleable.RatingStars_animation, 0)
        typedArray.recycle()
        init()
    }

    private fun init() {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT)
        for (i in 0..totalStars) {
            val imageView = ImageView(context)
            imageView.setImageDrawable(emptyDrawable)
            imageView.layoutParams = LayoutParams(100, 100)
            imageView.tag = i
            val outValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
            imageView.setBackgroundResource(outValue.resourceId)
            addView(imageView)
            imageView.setOnClickListener {
                setProgress(imageView.tag.toString().toInt())
            }
        }
        if (mRating > -1) setProgress(mRating)
    }

    private fun setProgress(position: Int) {
        for (i in 0 until position) {
            val imageView = this.getChildAt(i) as ImageView
            AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
                .apply {
                    duration = 50
                    start()
                    imageView.setImageDrawable(filledDrawable)
                }
        }

        val imageView = this.getChildAt(position) as ImageView
        when (animationType) {
            0 -> imageView.setImageDrawable(filledDrawable)
            1 -> alphaAnimation(imageView)
            2 -> rotateAnimation(imageView)
            3 -> scaleAnimation(imageView)
            4 -> ringAnimation(imageView)
            5 -> flipAnimation(imageView)
        }

        if (position == totalStars) {
            return
        }
        for (j in position + 1..totalStars) {
            val star = this.getChildAt(j) as ImageView
            star.setImageDrawable(emptyDrawable)
        }
    }

    private fun alphaAnimation(imageView: ImageView) {
        val fadeAnim = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f, 0f)
        fadeAnim.duration = 300
        fadeAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                imageView.setImageDrawable(filledDrawable)
                val anim = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 0.5f, 1f)
                anim.duration = 300
                anim.start()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        fadeAnim.start()
    }

    private fun rotateAnimation(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, "rotation", 0f, 180f)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(filledDrawable)
                        ObjectAnimator.ofFloat(imageView, "rotation", 180f, 360f)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
                start()
            }
    }

    private fun scaleAnimation(imageView: ImageView) {
        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 1.3f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 1.3f)
        ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(filledDrawable)
                        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f)
                        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f)
                        ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
                start()
            }
    }

    private fun ringAnimation(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, "translationY", 0F, -10F)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(filledDrawable)
                        ObjectAnimator.ofFloat(imageView, "translationY", -10F, 0F)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
                start()
            }
    }

    private fun flipAnimation(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, "rotationY", 0F, 180F)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(filledDrawable)
                        ObjectAnimator.ofFloat(imageView, "rotationY", 180F, 0F)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
                start()
            }
    }
}
