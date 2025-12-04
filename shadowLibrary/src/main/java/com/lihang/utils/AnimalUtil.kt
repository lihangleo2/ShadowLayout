package com.lihang.utils

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.view.View

/**
 * @Author leo
 * @Address https://github.com/lihangleo2
 * @Date 2024/5/6
 */
object AnimalUtil {
    @JvmStatic
    fun alpha(view: View, duration: Long = 500L, starAlpha: Float = 0f, endAlpha: Float = 1.0f) {
        view.animate().cancel()
        view.visibility = View.VISIBLE
        view.alpha = starAlpha
        view.animate()
                .alpha(endAlpha)
                .setDuration(duration)
                .setStartDelay(duration)
                .start()
    }


    @JvmStatic
    fun alphaHide(view: View, duration: Long = 500L, starAlpha: Float = 1.0f, endAlpha: Float =0f,delayDuration: Long = 500L) {
        view.animate().cancel()
        view.visibility = View.VISIBLE
        view.alpha = starAlpha
        view.animate()
                .alpha(endAlpha)
                .setDuration(duration)
                .setStartDelay(delayDuration)
                .setListener(object :AnimatorListener{
                    override fun onAnimationStart(animation: Animator) {
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator) {
                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                .start()
    }

    @JvmStatic
    fun cancleAnimate(view: View) {
        view.animate().cancel()
    }




    @JvmStatic
    fun bigNumAnim(view: View) {
        view.animate().cancel()
        view.scaleX = 1.2f
        view.scaleY = 1.2f
        view.pivotX = 0.5f
        view.pivotY = 0.5f
        view.animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(400)
                .start()
    }

}