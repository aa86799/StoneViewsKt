package com.stone.stoneviewskt.ui.mina.hhf.client

import com.stone.stoneviewskt.ui.mina.hhf.data.TransData
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolEncoderOutput
import org.apache.mina.filter.codec.demux.DemuxingProtocolEncoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


class TransDataEncoder : DemuxingProtocolEncoder() {

    val charset = Charset.forName("UTF-8")

    override fun encode(session: IoSession?, message: Any?, out: ProtocolEncoderOutput?) {
        if (message is TransData) {
            val (text, imgData, date) = message as? TransData ?: return

            // 编码字符串长度和内容
            val textBytes = text.toByteArray(StandardCharsets.UTF_8)
            val dateBytes = date?.toByteArray(StandardCharsets.UTF_8) ?: byteArrayOf()
            val buffer = IoBuffer.allocate((4 + textBytes.size) + (4 + imgData.size) + (4 + dateBytes.size))

            buffer.putInt(textBytes.size) // 文本长度
            buffer.put(textBytes)

            // 编码图片数据
            buffer.putInt(imgData.size)
            buffer.put(imgData)

            buffer.putInt(dateBytes.size)
            if (dateBytes.isNotEmpty()) {
                buffer.put(dateBytes)
            }

            buffer.flip()
            out!!.write(buffer)

            buffer.clear()
        }
    }

    override fun dispose(session: IoSession?) {

    }
}