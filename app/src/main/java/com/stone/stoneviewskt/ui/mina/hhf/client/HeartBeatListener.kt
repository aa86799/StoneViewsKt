package com.stone.stoneviewskt.ui.mina.hhf.client

import android.util.Log
import com.stone.stoneviewskt.ui.mina.hhf.manager.ClientConnectManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.mina.core.service.IoService
import org.apache.mina.core.service.IoServiceListener
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import org.apache.mina.transport.socket.nio.NioSocketConnector

/**
 * Created by huanghongfa on 2017/7/28.
 * 监听服务器断线原因
 */
class HeartBeatListener(var connector: NioSocketConnector) : IoServiceListener {

    @Throws(Exception::class)
    override fun serviceActivated(arg0: IoService) {
    }

    @Throws(Exception::class)
    override fun serviceDeactivated(arg0: IoService) {
    }

    @Throws(Exception::class)
    override fun serviceIdle(arg0: IoService, arg1: IdleStatus) {
    }

    @Throws(Exception::class)
    override fun sessionClosed(arg0: IoSession) {
        Log.d("", "hahahaha")
    }

    @Throws(Exception::class)
    override fun sessionCreated(arg0: IoSession) {
    }

    override fun sessionDestroyed(arg0: IoSession) {
        GlobalScope.launch {
            ClientConnectManager.instance?.rePeatConnect()
        }
    } /*
     * 断线重连操作
     * @param content
     */
    //    public void repeatConnect(String content) {
    //        // 执行到这里表示Session会话关闭了，需要进行重连,我们设置每隔3s重连一次,如果尝试重连5次都没成功的话,就认为服务器端出现问题,不再进行重连操作
    //        int count = 0;// 记录尝试重连的次数
    //        boolean isRepeat = false;
    //        while (!isRepeat && count <= 10) {
    //            try {
    //                count++;// 重连次数加1
    //                ConnectFuture future = connector.connect(new InetSocketAddress(
    //                        ConnectUtils.HOST, ConnectUtils.PORT));
    //                future.awaitUninterruptibly();// 一直阻塞住等待连接成功
    //                IoSession session = future.getSession();// 获取Session对象
    //                if (session.isConnected()) {
    //                    isRepeat = true;
    //                    // 表示重连成功
    //                    System.out.println(content + ConnectUtils.stringNowTime() + " : 断线重连" + count
    //                            + "次之后成功.....");
    //                    SessionManager.getInstance().setSeesion(session);
    //                    SessionManager.getInstance().writeToServer("重新连接的");
    //                    break;
    //                }
    //            } catch (Exception e) {
    //                if (count == ConnectUtils.REPEAT_TIME) {
    //                    System.out.println(content + ConnectUtils.stringNowTime() + " : 断线重连"
    //                            + ConnectUtils.REPEAT_TIME + "次之后仍然未成功,结束重连.....");
    //                    break;
    //                } else {
    //                    System.out.println(content + ConnectUtils.stringNowTime() + " : 本次断线重连失败,3s后进行第" + (count + 1) + "次重连.....");
    //                    try {
    //                        Thread.sleep(3000);
    //                        System.out.println(content + ConnectUtils.stringNowTime() + " : 开始第" + (count + 1) + "次重连.....");
    //                    } catch (InterruptedException e1) {
    //                        e1.printStackTrace();
    //                    }
    //                }
    //            }
    //        }
    //    }
}
