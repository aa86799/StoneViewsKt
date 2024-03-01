package com.stone.stoneviewskt.ui.mina.hhf.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.stone.stoneviewskt.ui.mina.hhf.client.TransCodecFactory
import com.stone.stoneviewskt.util.logi
import org.apache.mina.core.service.IoAcceptor
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import java.net.InetSocketAddress

/**
 * Created by huanghongfa on 2017/7/28.
 * 后台长连接服务
 */
class LongConnectService : Service() {

    private val TAG = "LongConnectService"
    private var acceptor: IoAcceptor? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startService()
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 启动服务
     */
    private fun startService() {
        try {
            if (acceptor != null && acceptor!!.isActive) {
                return
            }
            // 创建一个非阻塞的server端的Socket
            acceptor = NioSocketAcceptor()
            // 为接收器设置管理服务
            acceptor?.handler = ServiceHandler()

            acceptor?.filterChain?.addLast("trans", ProtocolCodecFilter(TransCodecFactory()))

            // 适合发送序列化对象，但要求该对象不超过 1mb
//            acceptor?.filterChain?.addLast("transCodec", ProtocolCodecFilter(ObjectSerializationCodecFactory()))

            // 设置过滤器（使用mina提供的文本换行符编解码器）   过滤器名称自行任意指定
//            acceptor?.filterChain?.addLast("codec", ProtocolCodecFilter(FrameCodecFactory()))
//            acceptor?.filterChain?.remove("encoder") // 移除指定过滤器
//            acceptor?.filterChain?.replace("encoder", AnotherProtocolCodecFilter()) // 替换指定过滤器

            // 设置读取数据的换从区大小
            acceptor?.sessionConfig?.readBufferSize = 2048
            acceptor?.sessionConfig?.maxReadBufferSize = 5*1024*1024 // 默认为1mb
            // 读写通道10秒内无操作进入空闲状态
            acceptor?.sessionConfig?.setIdleTime(IdleStatus.BOTH_IDLE, 10)
            // 绑定端口
            acceptor?.bind(InetSocketAddress(PORT))
            Log.d(TAG, "服务器启动成功(is active: ${acceptor?.isActive}) ... 端口号：" + PORT)
        } catch (e: Exception) {
            Log.d(TAG, "服务器启动异常...$e")
        }
    }

    private fun disposeServer() {
        if (acceptor != null && acceptor!!.isActive) {
            acceptor?.unbind()
            acceptor?.dispose()
            acceptor = null
            logi("server stop, server is active : ${acceptor?.isActive}")
        }
    }

    override fun onDestroy() {
        disposeServer()
        super.onDestroy()
    }

    /**
     * 初始化客户端MINA
     *
     * @param port
     */
    //    public void initClientMina(String host, int port) {
    //        NioSocketConnector connector = null;
    //        try {
    //            connector = new NioSocketConnector();
    //            HeartBeatHandler handler = new HeartBeatHandler();//创建handler对象，用于业务逻辑处理
    //            connector.setHandler(handler);
    //            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));//添加Filter对象
    //        } catch (Exception e2) {
    //            e2.printStackTrace();
    //            System.out.println(e2.toString());
    //        }
    //        connector.setConnectTimeout(ConnectUtils.TIMEOUT);//设置连接超时时间
    //        int count = 0;//记录连接次数
    //        while (true) {
    //            try {
    //                count++;
    //                //执行到这里表示客户端刚刚启动需要连接服务器,第一次连接服务器的话是没有尝试次数限制的，但是随后的断线重连就有次数限制了
    //                ConnectFuture future = connector.connect(new InetSocketAddress(ConnectUtils.HOST, ConnectUtils.PORT));
    //                future.awaitUninterruptibly();//一直阻塞,直到连接建立
    //                IoSession session = future.getSession();//获取Session对象
    //                if (session.isConnected()) {
    //                    //表示连接成功
    //                    System.out.println(ConnectUtils.stringNowTime() + " : 客户端连接服务器成功.....");
    //                    break;
    //                }
    //            } catch (RuntimeIoException e) {
    //                System.out.println(ConnectUtils.stringNowTime() + " : 第" + count + "次客户端连接服务器失败，因为" + ConnectUtils.TIMEOUT + "s没有连接成功");
    //                try {
    //                    Thread.sleep(2000);//如果本次连接服务器失败，则间隔2s后进行重连操作
    //                    System.out.println(ConnectUtils.stringNowTime() + " : 开始第" + (count + 1) + "次连接服务器");
    //                } catch (InterruptedException e1) {
    //                    e1.printStackTrace();
    //                }
    //            }
    //        }
    //        //为MINA客户端添加监听器，当Session会话关闭的时候，进行自动重连
    //        connector.addListener(new HeartBeatListener(connector));
    //    }
    companion object {
        // 端口号，要求客户端与服务器端一致
        private const val PORT = 3344
    }
}
