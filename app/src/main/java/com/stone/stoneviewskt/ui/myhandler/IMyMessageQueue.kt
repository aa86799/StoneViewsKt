package com.stone.stoneviewskt.ui.myhandler

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/3/27 11:09
 */
interface IMyMessageQueue {

    @Throws(InterruptedException::class)
    fun next(): MyMessage

    @Throws(InterruptedException::class)
    fun enqueueMessage(message: MyMessage)
}