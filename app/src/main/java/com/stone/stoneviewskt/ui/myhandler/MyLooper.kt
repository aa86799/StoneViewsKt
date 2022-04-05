package com.stone.stoneviewskt.ui.myhandler

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/3/29 07:57
 */
class MyLooper {

    var mMessageQueue: IMyMessageQueue = MyMessageQueue()

    companion object  {
        private val threadLocal = ThreadLocal<MyLooper?>() // 静态类变量，唯一；内部ThreadLocalMap，根据线程的不同，存取不同的有关联对象
        private var mainLooper: MyLooper? = null // 静态类变量,唯一

        fun prepare() {
            synchronized(this::class) {
                if (threadLocal.get() != null) {
                    throw RuntimeException("Only one looper can be created per thread.")
                }
                threadLocal.set(MyLooper())
            }
        }

        @Synchronized // 必须在主线程调用
        fun prepareMainLooper() {
            prepare()
            if (mainLooper != null) {
//                throw IllegalStateException("The main Looper has already been prepared.")
                return
            }
            mainLooper = myLooper()
        }

        fun myLooper(): MyLooper? {
            return threadLocal.get()
        }

        fun getMainLooper(): MyLooper? {
            return mainLooper
        }

        fun loop() {
            val looper = myLooper() ?: throw RuntimeException("No looper! MyLooper.prepare() wasn't called on this thread.")
            while (true) {
                try {
                    val msg = looper.mMessageQueue.next()
                    msg.target.dispatchMessage(msg)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}