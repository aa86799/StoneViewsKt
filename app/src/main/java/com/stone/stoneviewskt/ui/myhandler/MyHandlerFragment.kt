package com.stone.stoneviewskt.ui.myhandler

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.logi
import kotlinx.android.synthetic.main.fragment_my_handler.*

/**
 * desc:    handler --sendMessage(message)--> messageQueue;
 *          looper  从 messageQueue 循环取 message；
 *          取到的 message ，获取它的 target-handler； 执行 handlerMessage()，或 handler的 callback
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/3/27 10:56
 */
class MyHandlerFragment: BaseFragment() {

    private var handler: MyHandler? = null

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val t1 = Thread {
            logi("prepareMainLooper, loop()")
            MyLooper.prepareMainLooper()
            MyLooper.loop()
        }
        t1.start()
//        t1.join() // join()要求线程执行完毕；而 loop()是永真循环，不会主动结束

        btn_main_looper.setOnClickListener {
            Thread {
//                handler = MyHandler(MyLooper.getMainLooper()!!, object : MyHandler.Callback {
//                    override fun handleMessage(msg: MyMessage) {
//                        logi("${Thread.currentThread().name} callback: $msg")
//                    }
//                })
                handler = object : MyHandler(MyLooper.getMainLooper()!!) {
                    override fun handleMessage(msg: MyMessage) {
                        logi("${Thread.currentThread().name} : $msg")
                    }
                }
                val msg1 = MyMessage(1, "msg1-${Thread.currentThread().name}", handler!!)
                val msg2 = MyMessage(2, "msg2-${Thread.currentThread().name}", handler!!)
                handler?.sendMessage(msg1)
                Thread.sleep(1000)
                handler?.sendMessage(msg2)
            }.start()
        }
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_handler
    }
}