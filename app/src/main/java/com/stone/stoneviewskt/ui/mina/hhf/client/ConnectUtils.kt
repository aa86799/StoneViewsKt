package com.stone.stoneviewskt.ui.mina.hhf.client

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by huanghongfa on 2017/7/28.
 */
object ConnectUtils {
    const val REPEAT_TIME = 5 //表示重连次数
    const val HOST = "192.168.2.58" //表示IP地址
    const val PORT = 3344 //表示端口号
    const val IDLE_TIME = 10 //客户端10s内没有向服务端发送数据
    const val TIMEOUT = 5 //设置连接超时时间,超过5s还没连接上便抛出异常

    /**
     * 获取当前时间
     *
     * @return
     */
    @JvmStatic
    fun stringNowTime(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        return format.format(Date())
    }
}
