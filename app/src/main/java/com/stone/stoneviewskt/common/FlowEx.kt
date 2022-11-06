package com.stone.stoneviewskt.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/11/6 18:02
 */

public fun <T> Flow<T>.flowWithLifecycleEx(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = callbackFlow {
    var lastValue: T? = null
    lifecycle.repeatOnLifecycle(minActiveState) { // 一直挂起，直到 低于 min state，取消 协程
        this@flowWithLifecycleEx.collect {
            if (lastValue != it) {
                send(it)
            }
            lastValue = it
        }
    }
    lastValue = null // 最终取消时，才会置null
    close()
}
