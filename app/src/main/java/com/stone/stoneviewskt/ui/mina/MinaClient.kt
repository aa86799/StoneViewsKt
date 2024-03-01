//package com.stone.stoneviewskt.ui.mina
//
//import com.stone.stoneviewskt.util.logi
//import org.apache.mina.core.service.IoConnector
//import org.apache.mina.filter.codec.ProtocolCodecFilter
//import org.apache.mina.filter.codec.textline.TextLineCodecFactory
//import org.apache.mina.transport.socket.nio.NioSocketConnector
//import java.net.InetSocketAddress
//import java.nio.charset.Charset
//
//class MinaClient {
//
//    fun connect(msg: String) {
//        // 创建连接器
//        val connector: IoConnector = NioSocketConnector()
//
//        // 设置连接超时时间
//        connector.connectTimeoutMillis = 5_000
//
//        // 添加编解码器
//        connector.filterChain.addLast("codec",
//            ProtocolCodecFilter(TextLineCodecFactory(Charset.forName("UTF-8"))))
//
//        // 设置事件处理器
//        connector.handler = ClientSessionHandler()
//
//        // 连接到服务器
//        val future = connector.connect(InetSocketAddress(HOST, PORT))
//
//        // 等待连接创建完成
//        future.awaitUninterruptibly()
//
//        // 获取会话，并发送消息
//        val session = future.session
////        session.write("Hello from Android Mina Client!")
//        session.write(msg)
//
//        // 等待会话关闭，结束程序
//        session.closeFuture.awaitUninterruptibly()
//
//        // 释放连接器资源
//        connector.dispose()
//        logi("client: connector.dispose")
//    }
//
//    companion object {
//        private const val HOST = "192.168.2.58"
//        private const val PORT = 30123
//    }
//}
