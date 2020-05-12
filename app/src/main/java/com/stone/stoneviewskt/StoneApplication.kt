package com.stone.stoneviewskt

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
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
class StoneApplication : MultiDexApplication() {

    var mTopActivity: Activity? = null

    companion object {
        lateinit var instance: StoneApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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