package com.stone.stoneviewskt.ui.mina.hhf.service

import android.util.Log
import com.stone.stoneviewskt.StoneApplication
import com.stone.stoneviewskt.ui.mina.hhf.data.TransData
import com.stone.stoneviewskt.ui.mina.hhf.manager.SessionManager
import com.stone.stoneviewskt.util.showShort
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by huanghongfa on 2017/7/28.
 */
class ServiceHandler : IoHandlerAdapter() {
    private val TAG = "ServiceHandler"

    // 从端口接受消息，会响应此方法来对消息进行处理
    @Throws(Exception::class)
    override fun messageReceived(session: IoSession, message: Any) {
        super.messageReceived(session, message)
        Log.d(TAG, "服务器接受消息成功...")
        if (message is TransData) {
            // 向客户端发送消息
            val date = Date()
            message.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date)
            StoneApplication.instance.mTopActivity?.runOnUiThread {
                showShort("server to client trans message: $message")
            }
            session.write(message)
            return
        }
//        val msg = message.toString()
//        if ("exit" == msg) {
//            // 如果客户端发来exit，则关闭该连接
//            session.close(true)
//        }
//        // 向客户端发送消息
//        val date = Date()
//        StoneApplication.instance.mTopActivity?.runOnUiThread {
//            showShort("server to client message: $message")
//        }
//        session.write(date)
//        Log.d(TAG, "服务器接受消息成功...$msg")
    }

    // 向客服端发送消息后会调用此方法
    @Throws(Exception::class)
    override fun messageSent(session: IoSession, message: Any) {
        super.messageSent(session, message)
        //        session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
        Log.d(TAG, "服务器发送消息成功...")
    }

    // 关闭与客户端的连接时会调用此方法
    @Throws(Exception::class)
    override fun sessionClosed(session: IoSession) {
        super.sessionClosed(session)
        Log.d(TAG, "服务器与客户端断开连接...")
    }

    // 服务器与客户端创建连接
    @Throws(Exception::class)
    override fun sessionCreated(session: IoSession) {
        super.sessionCreated(session)
        Log.d(TAG, "服务器与客户端创建连接...")
    }

    // 服务器与客户端连接打开
    @Throws(Exception::class)
    override fun sessionOpened(session: IoSession) {
        Log.d(TAG, "服务器与客户端连接打开...")
        super.sessionOpened(session)
    }

    @Throws(Exception::class)
    override fun sessionIdle(session: IoSession, status: IdleStatus) {
        super.sessionIdle(session, status)
        Log.d(TAG, "服务器进入空闲状态...")
        SessionManager.getInstance().writeToServer("矁矁服务器")
    }

    // 当用户IoHandler实现或MINA抛出任何异常时调用
    @Throws(Exception::class)
    override fun exceptionCaught(session: IoSession, cause: Throwable) {
        super.exceptionCaught(session, cause)
        Log.d(TAG, "服务器发现异常..." + cause.message)
        session.closeNow()
    }
}
