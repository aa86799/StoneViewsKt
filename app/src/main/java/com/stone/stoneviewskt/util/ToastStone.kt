package com.stone.stoneviewskt.util

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.stone.stoneviewskt.StoneApplication

/**
 * desc:    Toast 单例
 * author:  stone
 * email:   aa86799@163.com
 * time:    2019-11-26 12:19
 */
private class ToastStone {

    val mToast: Toast = Toast.makeText(StoneApplication.instance, "", Toast.LENGTH_SHORT)

    companion object {
        val instance = ToastStone()
    }
}

fun showLong(msg: String) {
    ToastStone.instance.mToast.setText(msg)
    ToastStone.instance.mToast.duration = Toast.LENGTH_LONG
    ToastStone.instance.mToast.show()
}

fun showLong(@StringRes resId: Int) {
//    StoneApplication.instance.mTopActivity?.apply {
//        ToastFlash.instance.mToast.setText(getString(resId))
//        ToastFlash.instance.mToast.duration = Toast.LENGTH_LONG
//        ToastFlash.instance.mToast.show()
//    }
}

fun showShort(msg: String) {
    ToastStone.instance.mToast.setText(msg)
    ToastStone.instance.mToast.duration = Toast.LENGTH_SHORT
    ToastStone.instance.mToast.show()
}

fun showShort(@StringRes resId: Int) {
//    StoneApplication.instance.mTopActivity?.apply {
//        ToastFlash.instance.mToast.setText(getString(resId))
//        ToastFlash.instance.mToast.duration = Toast.LENGTH_SHORT
//        ToastFlash.instance.mToast.show()
//    }
}