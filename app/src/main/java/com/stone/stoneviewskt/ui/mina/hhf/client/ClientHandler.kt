package com.stone.stoneviewskt.ui.mina.hhf.client

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.stone.stoneviewskt.ui.mina.hhf.client.ConnectUtils.stringNowTime
import com.stone.stoneviewskt.ui.mina.hhf.data.TransData
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession

/**
 * Created by huanghongfa on 2017/7/28.
 */
class ClientHandler(private val mContext: Context?) : IoHandlerAdapter() {
    private val TAG = "ClientHandler"

    @Throws(Exception::class)
    override fun exceptionCaught(session: IoSession, cause: Throwable) {
        Log.d(TAG, stringNowTime() + " : 客户端调用exceptionCaught" + cause.message)
        session.closeNow()
    }

    @Throws(Exception::class)
    override fun messageReceived(session: IoSession, message: Any) {
        Log.e(TAG, "接收到服务器端消息：$message")
        if (message is TransData) {
            Log.e(TAG, "接收到服务器端消息：" + message.date)
        }
        if (mContext != null) {
            val intent = Intent(BROADCAST_ACTION)
            intent.putExtra(MESSAGE, message.toString())
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent)
        }
    }

    @Throws(Exception::class)
    override fun messageSent(session: IoSession, message: Any) {
        Log.d(TAG, stringNowTime() + " : 客户端调用messageSent")
        //        session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
    }

    @Throws(Exception::class)
    override fun sessionClosed(session: IoSession) {
        Log.d(TAG, stringNowTime() + " : 客户端调用sessionClosed")
    }

    @Throws(Exception::class)
    override fun sessionCreated(session: IoSession) {
        Log.d(TAG, stringNowTime() + " : 客户端调用sessionCreated")
    }

    @Throws(Exception::class)
    override fun sessionIdle(session: IoSession, status: IdleStatus) {
        Log.d(TAG, stringNowTime() + " : 客户端调用sessionIdle")
    }

    @Throws(Exception::class)
    override fun sessionOpened(session: IoSession) {
        Log.d(TAG, stringNowTime() + " : 客户端调用sessionOpened")
    }

    companion object {
        const val BROADCAST_ACTION = "com.commonlibrary.mina.broadcast"
        const val MESSAGE = "message"
    }
}
