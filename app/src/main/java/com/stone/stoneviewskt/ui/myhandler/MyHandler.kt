package com.stone.stoneviewskt.ui.myhandler

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/3/27 11:09
 */
open class MyHandler {

    private val mQueue: IMyMessageQueue
    private var mCallback: Callback? = null

    constructor() {
        mQueue = MyLooper.myLooper()!!.mMessageQueue
    }

    constructor(looper: MyLooper, callback: Callback? = null) {
        this.mQueue = looper.mMessageQueue
        this.mCallback = callback
    }

    interface Callback {
        fun handleMessage(msg: MyMessage)
    }

    fun sendMessage(msg: MyMessage) {
        msg.target = this
        try {
            mQueue.enqueueMessage(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun handleMessage(msg: MyMessage) {

    }

    fun dispatchMessage(msg: MyMessage) {
        if (mCallback != null) {
            mCallback?.handleMessage(msg)
            return
        }
        handleMessage(msg)
    }
}