package com.stone.stoneviewskt.util

import android.util.Log

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 09/06/2017 23 20
 */
private const val TAG = "stone-log--> "

fun logi(msg: String) {
    Log.i(TAG, msg)
}

fun loge(msg: String) {
    Log.e(TAG, msg)
}

fun loge(msg: String, throwable: Throwable) {
    Log.e(TAG, msg, throwable)
}