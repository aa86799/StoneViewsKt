package com.stone.stoneviewskt.ui.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.stone.stoneviewskt.util.logi

/**
 * desc   : 生命周期观察者
 * author : stone
 * email  : aa86799@163.com
 * time   : 2021/1/13 22:33
 */
class MyObserver : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                logi("ON_CREATE")
            }

            Lifecycle.Event.ON_START -> {
                logi( "ON_START")
            }

            Lifecycle.Event.ON_RESUME -> {
                logi( "ON_RESUME")
            }

            Lifecycle.Event.ON_PAUSE -> {
                logi( "ON_PAUSE")
            }

            Lifecycle.Event.ON_STOP -> {
                logi( "ON_STOP")
            }

            Lifecycle.Event.ON_DESTROY -> {
                logi( "ON_DESTROY")
            }

            Lifecycle.Event.ON_ANY -> {
                logi( "ON_ANY")
            }
        }
    }
}
