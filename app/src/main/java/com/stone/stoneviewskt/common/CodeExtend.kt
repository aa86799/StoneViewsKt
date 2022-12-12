package com.stone.stoneviewskt.common

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.util.loge
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/5/21 12:14
 */

/**
 * 利用 CoroutineScope 防抖
 *  若block中是 CoroutineScope.()  这样的，在每次调用 CoroutineScope 实例对象时，就会触发。
 * 会在最后一次 松开超时后， 执行 end
 */
private var <T : View> T.mDebounceSuspendJob: Job?
    get() = getTag(R.id.job_id) as? Job
    set(value) {
        setTag(R.id.job_id, value)
    }

fun <T : View> T.debounceClick(coroutineScope: CoroutineScope, delayMs: Long = 600L, block: suspend (T) -> Unit) {
    // 对于回调函数 block 中，要考虑 界面销毁，view 为null 的问题。 eg. 异步线程回调、网络请求回调
    setOnClickListener {
        mDebounceSuspendJob?.cancel()
        mDebounceSuspendJob = coroutineScope.launch {
            logi("start: ${System.currentTimeMillis()}")
            delay(delayMs)
            logi("end: ${System.currentTimeMillis()}")
            block(this@debounceClick)
            mDebounceSuspendJob = null
        }
    }
}

fun <T : View> T.debounceClick(owner: LifecycleOwner, delayMs: Long = 600L, block: suspend (T) -> Unit) {
    debounceClick(owner.lifecycle.coroutineScope, delayMs, block)
}

fun <T : View> T.debounceClick(coroutineScope: CoroutineScope, originBlock: View.OnClickListener?, delayMs: Long = 600L) {
    originBlock ?: return
    debounceClick(coroutineScope, delayMs) { originBlock.onClick(this) }
}

fun <T : View> T.debounceClick(owner: LifecycleOwner, originBlock: View.OnClickListener?, delayMs: Long = 600L) {
    originBlock ?: return
    debounceClick(owner.lifecycle.coroutineScope, originBlock, delayMs)
}

/**
 * 利用 Handler + Runnable 防抖
 */
private var <T : View> T.mDebounceHandleRunnable: Runnable?
    get() = getTag(R.id.runnable_id) as Runnable?
    set(value) {
        setTag(R.id.runnable_id, value)
    }

fun <T : View> T.debounceClickWidthHandler(delayMs: Long = 600L, callback: (T) -> Unit) {
    // 对于回调函数 callback 中，要考虑 界面销毁，view 为null 的问题。 eg. 异步线程回调、网络请求回调
    setOnClickListener {
        logi("start: ${System.currentTimeMillis()}  ${this.handler}")
        mDebounceHandleRunnable?.let {
            loge("remove")
            this.handler.removeCallbacks(it)
        }
        mDebounceHandleRunnable = Runnable {
            logi("end: ${System.currentTimeMillis()}")
            callback(this)
            mDebounceHandleRunnable = null
        }
        this.handler.postDelayed(mDebounceHandleRunnable!!, delayMs)
    }
}

fun <T : View> T.debounceClickWidthHandler(delayMs: Long = 600L, originBlock: View.OnClickListener?) {
    originBlock ?: return
    debounceClickWidthHandler(delayMs) { originBlock.onClick(this) }
}

/**
 * 记录系统时间，以判断 是否能执行真实点击事件。
 * 会一开始就触发真实回调，后面的连续快速点击，不会触发。
 */
private var <T : View> T.triggerLastTime: Long
    get() = getTag(R.id.trigger_last_time_id)?.toString()?.toLong() ?: 0
    set(value) {
        setTag(R.id.trigger_last_time_id, value)
    }

private fun <T : View> T.clickEnable(delayMs: Long): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= delayMs) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

@Suppress("UNCHECKED_CAST")
fun <T : View> T.clickWithTrigger(delayMs: Long = 600, block: (T) -> Unit) {
    setOnClickListener {
        logi("start: ${System.currentTimeMillis()}")
        if (clickEnable(delayMs)) {
            logi("end: ${System.currentTimeMillis()}")
            block(it as T)
        }
    }
}

fun <T : View> T.clickWithTrigger(originBlock: View.OnClickListener?, time: Long = 600) {
    originBlock ?: return
    clickWithTrigger(time) { originBlock.onClick(this) }
}