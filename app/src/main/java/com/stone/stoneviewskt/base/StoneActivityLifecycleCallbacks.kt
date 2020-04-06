package com.stone.stoneviewskt.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.stone.stoneviewskt.StoneApplication

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 15:15
 */
class StoneActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    val mActivityList : MutableList<Activity> = mutableListOf()

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        mActivityList.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        StoneApplication.instance.mTopActivity = activity
        mActivityList.add(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        
    }

}