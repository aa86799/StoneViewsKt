package com.stone.stoneviewskt

import android.app.Activity
import android.app.AppOpsManager
import android.app.AsyncNotedAppOp
import android.app.SyncNotedAppOp
import android.content.Context
import android.os.Build
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//android 30
            //当调用需要运行时权限的方法时，会触发回调，应用内只能注册一个，后注册覆盖前一个
            val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager


            /*
             * 分派事件的线程
             * Context#getMainExecutor()  是 api 28添加的 ，不通用
             * AsyncTask.THREAD_POOL_EXECUTOR、Executors.newSingleThreadExecutor()
             */
            appOpsManager.setOnOpNotedCallback(mainExecutor, object : AppOpsManager.OnOpNotedCallback() {
                override fun onNoted(snop: SyncNotedAppOp) {//同步
                    logi("permission: ${snop.op}")
                }

                override fun onSelfNoted(p0: SyncNotedAppOp) {

                }

                override fun onAsyncNoted(p0: AsyncNotedAppOp) {//异步触发

                }

            })
        }
    }

}