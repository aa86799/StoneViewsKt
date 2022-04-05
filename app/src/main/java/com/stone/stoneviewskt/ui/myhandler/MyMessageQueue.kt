package com.stone.stoneviewskt.ui.myhandler

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/3/27 11:10
 */
class MyMessageQueue : IMyMessageQueue {

    private val mQueue: BlockingQueue<MyMessage>

    init {
        mQueue = LinkedBlockingQueue()
    }

    override fun next(): MyMessage {
        return mQueue.take()
    }

    override fun enqueueMessage(message: MyMessage) {
        mQueue.put(message)
    }
}