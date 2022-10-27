package com.stone.stoneviewskt

import android.app.Activity
import android.app.AppOpsManager
import android.app.AsyncNotedAppOp
import android.app.SyncNotedAppOp
import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.stone.stoneviewskt.base.StoneActivityLifecycleCallbacks
import com.stone.stoneviewskt.service.CesManager
import com.stone.stoneviewskt.service.core.SuperCross
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
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        lateinit var instance: StoneApplication
        const val DOKIT_PID= "5e73502e25295284cd3a812b95af99a0"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(StoneActivityLifecycleCallbacks())

        SuperCross.instance.init(this)
        SuperCross.instance.register(CesManager::class.java)

//        val kits = mutableListOf<AbstractKit>()
//        kits.add(CpuKit())
//        kits.add(WebDoorKit())
//        kits.add(LargePictureKit())
//        kits.add(FileExplorerKit())
//        kits.add(ColorPickerKit())
//        kits.add(LayoutBorderKit())
//        kits.add(UIPerformanceKit())
//        kits.add(WeakNetworkKit())
//        kits.add(GpsMockKit())
//        kits.add(NetworkKit())
//        kits.add(SysInfoKit())
//        kits.add(CrashCaptureKit())
//        kits.add(BlockMonitorKit())
//        kits.add(LogInfoKit())
//        kits.add(FrameInfoKit())
//        kits.add(H5Kit())
//        DoraemonKit.install(this, kits, DOKIT_PID);

        val rootDir = MMKV.initialize(this) //腾讯mmkv：key-value
        // 创建自己的实例  参数1：库的key， 参数2：库的模式（多进程或单进程）
        MMKV.mmkvWithID("stoneViewsKt", MMKV.MULTI_PROCESS_MODE)
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