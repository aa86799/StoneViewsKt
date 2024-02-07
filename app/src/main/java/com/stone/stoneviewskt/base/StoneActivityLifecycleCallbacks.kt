package com.stone.stoneviewskt.base

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.StoneApplication
import com.stone.stoneviewskt.util.loge
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.displayMetrics

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 15:15
 */
class StoneActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private val mActivityList: MutableList<Activity> = mutableListOf()

    override fun onActivityPaused(activity: Activity) {
        loge("onActivityPaused ${activity.javaClass.simpleName}")
    }

    override fun onActivityStarted(activity: Activity) {
        loge("onActivityStarted ${activity.javaClass.simpleName}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        loge("onActivityDestroyed ${activity.javaClass.simpleName}")
        mActivityList.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        loge("onActivitySaveInstanceState ${activity.javaClass.simpleName}")
    }

    override fun onActivityStopped(activity: Activity) {
        loge("onActivityStopped ${activity.javaClass.simpleName}")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        loge("onActivityCreated ${activity.javaClass.simpleName}")
        StoneApplication.instance.mTopActivity = activity
        mActivityList.add(activity)

        if (activity.javaClass.simpleName == "MainActivity") {
            addFullViewWithAnimator(activity)
        }

        // startActivity后，新创建的 activity 先执行 enterAnim；前一个 Activity 执行 exitAnim
        activity.overridePendingTransition(R.anim.activity_x_rtl_enter, R.anim.activity_x_rtl_exit)
    }

    override fun onActivityResumed(activity: Activity) {
        loge("onActivityResumed ${activity.javaClass.simpleName}")
    }

    private fun addFullViewWithAnimator(activity: Activity) {
        val content = activity.findViewById<FrameLayout>(android.R.id.content)
        val fv = FrameLayout(activity)
        fv.layoutParams = FrameLayout.LayoutParams(-1, -1)
        fv.backgroundColor = Color.parseColor("#aaff00f0")
        content.post {
            content.addView(fv)
            val height = activity.displayMetrics.heightPixels.toFloat()
//            val ani = ObjectAnimator.ofFloat(fv, "alpha", 0f, 1f)
            val ani = ObjectAnimator.ofFloat(fv, "translationY", -height, 0f, -height)
            ani.duration = 1000
            ani.repeatCount = 2 //ValueAnimator.INFINITE
            ani.repeatMode = ValueAnimator.RESTART
            ani.start()
            var flag = false
            ani.addUpdateListener {
                val fraction = it.animatedFraction
                if (fraction > 0.5f && !flag) {
                    AlertDialog.Builder(activity).setMessage("addFullViewWithAnimator").create().show()
                    flag = true
                }
            }
            ani.addListener(object : Animator.AnimatorListener {

                override fun onAnimationRepeat(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    content.removeView(fv)
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationStart(animation: Animator) {
                }
            })
        }
    }

}