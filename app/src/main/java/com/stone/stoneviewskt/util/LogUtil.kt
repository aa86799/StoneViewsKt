package com.stone.stoneviewskt.util

import android.util.Log
import com.stone.stoneviewskt.BuildConfig

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 09/06/2017 23 20
 */
private const val TAG = "stone-log--> "

fun logi(msg: String) {
    if (BuildConfig.DEBUG)
        Log.i(TAG, msg)
}

fun loge(msg: String) {
    if (BuildConfig.DEBUG)
        Log.e(TAG, msg)
}

fun loge(msg: String, throwable: Throwable) {
    if (BuildConfig.DEBUG)
        Log.e(TAG, msg, throwable)
}