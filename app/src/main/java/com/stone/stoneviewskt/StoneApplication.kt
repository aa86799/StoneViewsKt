package com.stone.stoneviewskt

import android.app.Activity
import android.app.Application
import com.stone.stoneviewskt.base.StoneActivityLifecycleCallbacks

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 14:27
 */
class StoneApplication : Application() {

    var mTopActivity: Activity? = null

    companion object {
        lateinit var instance: StoneApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(StoneActivityLifecycleCallbacks())
    }

}