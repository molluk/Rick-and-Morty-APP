package com.resource

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.floatDp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.floatDp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

fun View.fadeVisibility(visibility: Int, duration: Long = 300) {
    val transition: Transition = Fade()
    transition.duration = duration
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.visibility = visibility
}

fun View.slideVisibility(visibility: Int, duration: Long = 200) {
    val transition: Transition = Slide()
    transition.duration = duration
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.visibility = visibility
}


fun View.fadeVisibility(visibility: Int, duration: Long = 300, postDelay: Long = 250) {
    if (visibility != this.visibility) {
        postDelayed({
            val fadeIn = if (visibility == View.VISIBLE) 1f else 0f
            val fadeTo = if (visibility == View.VISIBLE) 0f else 1f
            this.alpha = fadeIn
            this.animate()
                .alpha(fadeTo)
                .setDuration(duration)
                .setInterpolator(DecelerateInterpolator())
                .start().let {
                    this.visibility = visibility
                }
        }, postDelay)
    }
}

fun View.customRotate(from: Float, to: Float, duration: Long, fillAfter: Boolean) {
    val rotate = RotateAnimation(
        from,
        to,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    rotate.duration = duration
    rotate.fillAfter = fillAfter
    rotate.interpolator = LinearInterpolator()
    this.startAnimation(rotate)
}

