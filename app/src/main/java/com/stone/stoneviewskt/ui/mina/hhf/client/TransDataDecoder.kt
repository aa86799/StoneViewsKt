package com.stone.stoneviewskt.ui.mina.hhf.client

import com.stone.stoneviewskt.ui.mina.hhf.data.TransData
import com.stone.stoneviewskt.util.logi
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.CumulativeProtocolDecoder
import org.apache.mina.filter.codec.ProtocolDecoderOutput
import java.nio.charset.StandardCharsets


class TransDataDecoder: CumulativeProtocolDecoder() {

    override fun doDecode(session: IoSession, buffer: IoBuffer, out: ProtocolDecoderOutput): Boolean {
        // 解码字符串长度和内容
        if (buffer.remaining() >= 4) { // text length 是int 类型 占4字节
            val textLength: Int = buffer.int
            if (buffer.remaining() >= textLength) {
                val textBytes = ByteArray(textLength)
                buffer.get(textBytes)
                val text = String(textBytes, StandardCharsets.UTF_8)

                if (buffer.remaining() >= 4) { // imgData length 4字节整数
                    val imgDataLength = buffer.int
                    logi("imgDataLength: $imgDataLength")
                    if (buffer.remaining() >= imgDataLength) {
                        val imgData = ByteArray(imgDataLength)
                        buffer.get(imgData)

                        if (buffer.remaining() >= 4) { // date length 4字节整数
                            val dateLength = buffer.int
                            if (buffer.remaining() >= dateLength && dateLength != 0) {
                                val date = ByteArray(dateLength)
                                buffer.get(date)
                                logi("decode: ${String(date)}")
                                out.write(TransData(text, imgData, String(date)))
                            } else {
                                out.write(TransData(text, imgData))
                            }
                            buffer.clear()
                        } else {
                            buffer.rewind()
                            return false
                        }
                    } else {
                        buffer.rewind()
                        return false
                    }
                } else {
                    buffer.rewind()
                    return false
                }

                // 剩余的数据作为图片数据
//                val imgData = ByteArray(buffer.remaining())
//                buffer.get(imgData)
//                val data = TransData(text, imgData)
//                out.write(data)
            } else {
                buffer.rewind() // 数据不足，重置到标记的位置
                return false
            }
        }
        /*
        当doDecode方法返回true时，框架期望所有的输入数据都已经被处理，以便可以安全地处理下一个消息。
        如果还有未处理的数据留在缓冲区中，框架就不知道如何处理剩余的数据，因此抛出了这个异常。
        java.lang.IllegalStateException: doDecode() can't return true when buffer is not consumed.

        如果出于某种原因，你的解码器无法处理缓冲区中剩余的所有数据，
        考虑使用IoBuffer的compact方法将未处理的数据移动到缓冲区的开始位置，或者使用skip方法跳过一些数据。
        但是，这通常是不推荐的，因为它可能会导致数据丢失或解码错误。
         */
        return true
    }

    override fun finishDecode(session: IoSession?, out: ProtocolDecoderOutput?) {
        
    }

    override fun dispose(session: IoSession?) {
        
    }


}