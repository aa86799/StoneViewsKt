package com.stone.stoneviewskt.common

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.stone.stoneviewskt.util.loge
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

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
private val jobUuid = UUID.randomUUID().hashCode()
private var View.mDebounceSuspendJob: Job?
    get() = if (getTag(jobUuid) != null) getTag(jobUuid) as Job? else null
    set(value) {
        setTag(jobUuid, value)
    }

fun View.debounceClick(coroutineScope: CoroutineScope, delayMs: Long = 600L, block: suspend () -> Unit) {
    // 对于回调函数 block 中，要考虑 界面销毁，view 为null 的问题。 eg. 异步线程回调、网络请求回调
    setOnClickListener {
        mDebounceSuspendJob?.cancel()
        mDebounceSuspendJob = coroutineScope.launch {
            logi("start: ${System.currentTimeMillis()}")
            delay(delayMs)
            logi("end: ${System.currentTimeMillis()}")
            block()
            mDebounceSuspendJob = null
        }
    }
}

fun View.debounceClick(owner: LifecycleOwner, delayMs: Long = 600L, block: suspend () -> Unit) {
    debounceClick(owner.lifecycle.coroutineScope, delayMs, block)
}

fun View.debounceClick(coroutineScope: CoroutineScope, originBlock: View.OnClickListener?, delayMs: Long = 600L) {
    originBlock ?: return
    debounceClick(coroutineScope, delayMs) { originBlock.onClick(this) }
}

fun View.debounceClick(owner: LifecycleOwner, originBlock: View.OnClickListener?, delayMs: Long = 600L) {
    originBlock ?: return
    debounceClick(owner.lifecycle.coroutineScope, originBlock, delayMs)
}

/**
 * 利用 Handler + Runnable 防抖
 */
private val debounceHandle: Handler by lazy { Handler(Looper.getMainLooper()) }

private val runnableUuid = UUID.randomUUID().hashCode()
private var View.mDebounceHandleRunnable: Runnable?
    get() = if (getTag(runnableUuid) != null) getTag(runnableUuid) as Runnable? else null
    set(value) {
        setTag(runnableUuid, value)
    }

fun View.debounceClickWidthHandler(delayMs: Long = 600L, callback: () -> Unit) {
    // 对于回调函数 callback 中，要考虑 界面销毁，view 为null 的问题。 eg. 异步线程回调、网络请求回调
    setOnClickListener {
        logi("start: ${System.currentTimeMillis()}  $debounceHandle")
        mDebounceHandleRunnable?.let {
            loge("remove")
            debounceHandle.removeCallbacks(it)
        }
        mDebounceHandleRunnable = Runnable {
            logi("end: ${System.currentTimeMillis()}")
            callback()
            mDebounceHandleRunnable = null
        }
        debounceHandle.postDelayed(mDebounceHandleRunnable!!, delayMs)
    }
}

fun View.debounceClickWidthHandler(delayMs: Long = 600L, originBlock: View.OnClickListener?) {
    originBlock ?: return
    debounceClickWidthHandler(delayMs) { originBlock.onClick(this) }
}

/**
 * 记录系统时间，以判断 是否能执行真实点击事件。
 * 会一开始就触发真实回调，后面的连续快速点击，不会触发。
 */
fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit) {
    triggerDelay = time
    setOnClickListener {
        logi("start: ${System.currentTimeMillis()}")
        if (clickEnable()) {
            logi("end: ${System.currentTimeMillis()}")
            block(it as T)
        }
    }
}

fun <T : View> T.clickWithTrigger(originBlock: View.OnClickListener?, time: Long = 600) {
    originBlock ?: return
    clickWithTrigger(time) { originBlock.onClick(this) }
}

private val triggerLastTimeUuid = UUID.randomUUID().hashCode()
private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(triggerLastTimeUuid) != null) getTag(triggerLastTimeUuid) as Long else 0
    set(value) {
        setTag(triggerLastTimeUuid, value)
    }

private val triggerTimeUuid = UUID.randomUUID().hashCode()
private var <T : View> T.triggerDelay: Long
    get() = if (getTag(triggerTimeUuid) != null) getTag(triggerTimeUuid) as Long else -1
    set(value) {
        setTag(triggerTimeUuid, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}