package com.stone.stoneviewskt.ui.mina.hhf.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.StoneApplication
import com.stone.stoneviewskt.common.dispatchDefault
import com.stone.stoneviewskt.ui.mina.hhf.client.ClientHandler
import com.stone.stoneviewskt.ui.mina.hhf.client.ConnectUtils
import com.stone.stoneviewskt.ui.mina.hhf.client.HeartBeatListener
import com.stone.stoneviewskt.ui.mina.hhf.client.HeartBeatMessageFactory
import com.stone.stoneviewskt.ui.mina.hhf.client.TransCodecFactory
import com.stone.stoneviewskt.ui.mina.hhf.data.TransData
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.delay
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.keepalive.KeepAliveFilter
import org.apache.mina.transport.socket.nio.NioSocketConnector
import java.io.ByteArrayOutputStream
import java.net.InetSocketAddress
import kotlin.random.Random

/**
 * Created by huanghongfa on 2017/7/29.
 */
class ClientConnectManager private constructor() {

    private var context: Context? = null
    private var mSession: IoSession? = null

    fun init(context: Context?) {
        this.context = context
    }

    suspend fun connect(context: Context?) {
        dispatchDefault {
            if (mSession != null) {
                if (mSession!!.isConnected) {
                    logi("session 复用")
                    SessionManager.getInstance().setSession(mSession)
//                SessionManager.getInstance().writeToServer("client 消息来了 ${System.currentTimeMillis()}")

                    val bm = BitmapFactory.decodeResource(StoneApplication.instance.mTopActivity!!.resources, R.mipmap.back_girl_test)
                    val baos = ByteArrayOutputStream()
                    bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    logi("total data " + baos.size().toString())
                    val transData = TransData("test${Random.nextInt()}", baos.toByteArray())
                    SessionManager.getInstance().writeToServer(transData)
                    baos.reset()
                    bm.recycle()
                }
                return@dispatchDefault
            }
            val mSocketConnector = NioSocketConnector()
            //设置协议封装解析处理
//            mSocketConnector.filterChain.addLast("codec", ProtocolCodecFilter(FrameCodecFactory()))
            //设置心跳包
            val heartFilter = KeepAliveFilter(HeartBeatMessageFactory())
            //每 5 分钟发送一个心跳包
            heartFilter.requestInterval = 5 * 60
            //心跳包超时时间 10s
            heartFilter.requestTimeout = 10
            // 获取过滤器链
            val filterChain = mSocketConnector.filterChain
            filterChain?.addLast("trans", ProtocolCodecFilter(TransCodecFactory()))
//            mSocketConnector?.filterChain?.addLast("transCodec", ProtocolCodecFilter(ObjectSerializationCodecFactory()))
//            // 添加编码过滤器 处理乱码、编码问题
//            filterChain.addLast("codec", ProtocolCodecFilter(FrameCodecFactory()))
            mSocketConnector.filterChain.addLast("heartbeat", heartFilter)
            //设置 handler 处理业务逻辑
            mSocketConnector.handler = ClientHandler(context)
            mSocketConnector.addListener(HeartBeatListener(mSocketConnector))
            mSocketConnector?.sessionConfig?.maxReadBufferSize = 5*1024*1024
            //配置服务器地址
            val mSocketAddress = InetSocketAddress(ConnectUtils.HOST, ConnectUtils.PORT)
            //发起连接
            val mFuture = mSocketConnector.connect(mSocketAddress)
            mFuture.awaitUninterruptibly()
            mSession = mFuture.session
            mSession?.config?.maxReadBufferSize = 5*1024*1024 // 默认为1mb
            Log.d("", "======连接成功$mSession")
            if (mSession!!.isConnected) {
                SessionManager.getInstance().setSession(mSession)
//                SessionManager.getInstance().writeToServer("client 消息来了 ${System.currentTimeMillis()}")

                val bm = BitmapFactory.decodeResource(StoneApplication.instance.mTopActivity!!.resources, R.mipmap.back_girl_test)
                val baos = ByteArrayOutputStream()
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
                logi("total data " + baos.size().toString())
                val transData = TransData("test：${Random.nextInt()}", baos.toByteArray())
                logi("trans.data " + transData.data)
                SessionManager.getInstance().writeToServer(transData)
                baos.close()
                baos.reset()
            }
        }

    }

    suspend fun rePeatConnect() {
        val isRepeat = booleanArrayOf(false)
        dispatchDefault {
            // 执行到这里表示Session会话关闭了，需要进行重连,我们设置每隔3s重连一次,如果尝试重连5次都没成功的话,就认为服务器端出现问题,不再进行重连操作
            var count = 0 // 记录尝试重连的次数
            var mSocketConnector: NioSocketConnector? = null
            while (!isRepeat[0] && count < 10) {
                try {
                    count++
                    if (mSocketConnector == null) {
                        mSocketConnector = NioSocketConnector()
                    }
                    //设置协议封装解析处理
//                    mSocketConnector.filterChain.addLast("protocol", ProtocolCodecFilter(FrameCodecFactory()))
                    //设置心跳包
                    val heartFilter = KeepAliveFilter(HeartBeatMessageFactory())
                    //每 5 秒发送一个心跳包
                    heartFilter.requestInterval = 5
                    //心跳包超时时间 10s
                    heartFilter.requestTimeout = 10
                    // 获取过滤器链
                    val filterChain = mSocketConnector.filterChain
                    filterChain?.addLast("trans", ProtocolCodecFilter(TransCodecFactory()))
//                    filterChain?.addLast("transCodec", ProtocolCodecFilter(ObjectSerializationCodecFactory()))
//                    // 添加编码过滤器 处理乱码、编码问题
//                    filterChain.addLast("codec", ProtocolCodecFilter(FrameCodecFactory()))
                    mSocketConnector.filterChain.addLast("heartbeat", heartFilter)
                    //设置 handler 处理业务逻辑
                    mSocketConnector.handler = ClientHandler(context)
                    mSocketConnector.addListener(HeartBeatListener(mSocketConnector))
                    //配置服务器地址
                    val mSocketAddress = InetSocketAddress(ConnectUtils.HOST, ConnectUtils.PORT)
                    //发起连接
                    val mFuture = mSocketConnector.connect(mSocketAddress)
                    mFuture.awaitUninterruptibly()
                    val mSession = mFuture.session
                    if (mSession.isConnected) {
                        isRepeat[0] = true
                        Log.d("", "======连接成功$mSession")
                        SessionManager.getInstance().setSession(mSession)
                        SessionManager.getInstance().writeToServer("重新连接发起的请求")
                        break
                    }
                } catch (e1: Exception) {
                    SessionManager.getInstance().closeSession()
                    if (count == ConnectUtils.REPEAT_TIME) {
                        logi(ConnectUtils.stringNowTime() + " : 断线重连"
                                + ConnectUtils.REPEAT_TIME + "次之后仍然未成功,结束重连.....")
                        break
                    } else {
                        logi(ConnectUtils.stringNowTime() + " : 本次断线重连失败,3s后进行第" + (count + 1) + "次重连.....")
                        try {
                            delay(3000)
                            logi(ConnectUtils.stringNowTime() + " : 开始第" + (count + 1) + "次重连.....")
                        } catch (e12: InterruptedException) {
                            Log.e("rePeatConnect ", "rePeatConnect e12$e12")
                        }
                    }
                }
            }
        }
    }

    companion object {
        var instance: ClientConnectManager? = null
            get() {
                if (null == field) {
                    field = ClientConnectManager()
                }
                return field
            }
            private set
    }
}
