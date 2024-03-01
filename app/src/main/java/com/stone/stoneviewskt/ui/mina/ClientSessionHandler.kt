//package com.stone.stoneviewskt.ui.mina
//
//import com.stone.stoneviewskt.StoneApplication
//import com.stone.stoneviewskt.ui.mina.hhf.data.TransData
//import com.stone.stoneviewskt.util.logi
//import com.stone.stoneviewskt.util.showShort
//import org.apache.mina.core.service.IoHandlerAdapter
//import org.apache.mina.core.session.IoSession
//
//// 非 复杂示例
//class ClientSessionHandler : IoHandlerAdapter() {
//
//    @Throws(Exception::class)
//    override fun messageReceived(session: IoSession, message: Any) {
//        if (message is TransData) {
//            logi("client message received: $message")
//            StoneApplication.instance.mTopActivity?.runOnUiThread {
//                showShort("client message received: $message")
//            }
//            return
//        }
//        // 当接收到服务器发送的消息时触发
//        logi("client message received: $message")
//        StoneApplication.instance.mTopActivity?.runOnUiThread {
//            showShort("client message received: $message")
//        }
//    }
//
//    @Throws(Exception::class)
//    override fun exceptionCaught(session: IoSession, cause: Throwable) {
//        // 处理任何异常
//        cause.printStackTrace()
//    }
//}
