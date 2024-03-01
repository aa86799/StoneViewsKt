package com.stone.stoneviewskt.ui.mina

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentMinaBinding
import com.stone.stoneviewskt.ui.mina.hhf.manager.ClientConnectManager
import com.stone.stoneviewskt.ui.mina.hhf.manager.SessionManager
import com.stone.stoneviewskt.ui.mina.hhf.service.LongConnectService
import kotlinx.coroutines.launch

/**
 * desc:    使用 Apache Mina，进行 socket 连接
 *      https://mina.apache.org/mina-project/userguide/user-guide-toc.html
 *      https://mina.apache.org/mina-project/developer-guide.html
 *      https://mina.apache.org/mina-project/documentation.html
 * author:  stone
 * email:   aa86799@163.com
 * time:    2024/2/27 09:16
 */
class MinaFragment: BaseBindFragment<FragmentMinaBinding>(R.layout.fragment_mina) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.btnStartServer.setOnClickListener {
//            MinaServer().start()
//            mBind.btnClient.isEnabled = false
        }

        mBind.btnClient.setOnClickListener {
//            mBind.btnStartServer.isEnabled = false
//            lifecycleScope.launch {
//                dispatchDefault {
//                    (1..50).forEach {
//                        // 只连接上一次，发送一次数据，多线程出了问题 u积
//                        MinaClient().connect("msg: $it")
//                        delay(10_000)
//                    }
//                }
//            }
        }

        mBind.btnStopServer.setOnClickListener {
//            MinaServer().stop()
//            mBind.btnClient.isEnabled = false
        }

        mBind.btnLong.setOnClickListener {
            // https://github.com/kevinStrange/MinaClient
            ClientConnectManager.instance?.init(requireContext())
            val intent = Intent(requireContext(), LongConnectService::class.java)
            requireContext().startService(intent)
        }

        mBind.btnLongClient.setOnClickListener {
            lifecycleScope.launch {
                SessionManager.getInstance().closeSession()
                ClientConnectManager.instance?.connect(requireContext())
            }
        }

        mBind.btnLongStopServer.setOnClickListener {
            requireContext().stopService(Intent(requireContext(), LongConnectService::class.java))
        }
        /*
         * 如果仅使用 一种 消息收发类型，比如纯 string 类型，参考 FrameCodecFactory/FrameEncoder/FrameDecoder；
         *
         * 如果 收发对象是 自定义类型，可，使数据类实现序列化接口，使用 ObjectSerializationCodecFactory/ObjectSerializationEncoder/ObjectSerializationDecoder;
         *     但会受到 传输大小为1mb 的限制
         *
         * 如果 收发对象是 自定义类型，而 自定义实现 TransCodecFactory/TransDataEncoder/TransDataDecoder；
         *      自定义协议，将 类型的属性，一个个 encoder；
         *      再 一个个 decoder，组成 发送类型；
         * 注意：若收发类型不相同，编解码可能就无法友好支持了。
         *
         * demo 的流程：LongConnectService 启动 服务端；
         *      ClientConnectManager 客户端配置编解码过滤器，连接服务端，发送 TransData；
         *          客户端触发 TransDataEncoder#encode()；
         *          然后服务端触发 TransDataDecoder#decode()；
         *          ServiceHandler#messageReceived() 接收客户端消息；
         *              再 回传客户端，又经历 encode()；
         *              客户端收到 decode()，ClientHandler#messageReceived()
         *
         */
    }

    override fun onDestroy() {
        SessionManager.getInstance().closeSession()
        super.onDestroy()
    }
}