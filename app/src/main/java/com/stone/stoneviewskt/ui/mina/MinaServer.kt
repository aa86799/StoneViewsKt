//package com.stone.stoneviewskt.ui.mina
//
//import org.apache.mina.core.service.IoAcceptor
//import org.apache.mina.core.session.IdleStatus
//import org.apache.mina.filter.codec.ProtocolCodecFilter
//import org.apache.mina.filter.codec.textline.TextLineCodecFactory
//import org.apache.mina.filter.logging.LoggingFilter
//import org.apache.mina.transport.socket.nio.NioSocketAcceptor
//import java.io.IOException
//import java.net.InetSocketAddress
//import java.nio.charset.Charset
//
//class MinaServer {
//
//    private lateinit var acceptor: IoAcceptor
//
//    fun start() {
//        // 创建接收器
//        acceptor = NioSocketAcceptor()
//        acceptor.filterChain.addLast( "logger", LoggingFilter() )
//        // 添加编解码器
//        acceptor.filterChain.addLast("codec",
//            ProtocolCodecFilter(TextLineCodecFactory(Charset.forName("UTF-8"))))
//
//        // 设置读缓冲区大小
//        acceptor.sessionConfig.readBufferSize = 2048
//
//        // 设置空闲时间检测
//        acceptor.sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 10)
//
//        // 设置事件处理器
//        acceptor.handler = ServerSessionHandler()
//        try {
//            // 绑定监听端口
//            acceptor.bind(InetSocketAddress(PORT))
//            println("MINA Server is listening at port " + PORT)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun stop() {
//        if (acceptor.isActive) {
//            acceptor.unbind()
//            acceptor.dispose()
//        }
//    }
//
//    companion object {
//        private const val PORT = 30123
//    }
//}
