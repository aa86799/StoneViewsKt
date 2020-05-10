package com.stone.stoneviewskt

import android.app.Activity
import android.app.Application
import com.stone.stoneviewskt.base.StoneActivityLifecycleCallbacks
import com.stone.stoneviewskt.util.logi
import com.tencent.mmkv.MMKV

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

        val rootDir = MMKV.initialize(this)
        logi("MMKV rootDir: $rootDir")
//        MMKV.defaultMMKV().encode()
//        MMKV.defaultMMKV().decodeBool()
    }

}