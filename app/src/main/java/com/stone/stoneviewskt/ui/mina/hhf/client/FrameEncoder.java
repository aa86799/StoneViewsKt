package com.stone.stoneviewskt.ui.mina.hhf.client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;

import java.nio.charset.Charset;

/**
 * Created by huanghongfa on 2017/7/28.
 * 负责将高级的消息对象编码成原始的二进制或文本数据，以便通过网络发送。
 */
public class FrameEncoder implements ProtocolEncoder {
    private final static Charset charset = Charset.forName("UTF-8");
    @Override
    public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        IoBuffer buff = IoBuffer.allocate(100).setAutoExpand(true);
        buff.putString(message.toString(), charset.newEncoder());
        // put 当前系统默认换行符
        buff.putString(LineDelimiter.DEFAULT.getValue(), charset.newEncoder());
        buff.flip();

        protocolEncoderOutput.write(buff);
//        if (o instanceof String) {
//            String messageString = (String) o;
//            //封装为 Frame 协议
//            byte[] messageBytes = messageString.getBytes(Charset.forName("UTF-8"));
//            int totalSize = messageBytes.length + 4;
//            IoBuffer buffer = IoBuffer.allocate(totalSize);
//            buffer.putInt(totalSize);
//            buffer.put(messageBytes);
//            buffer.flip();
//            protocolEncoderOutput.write(buffer);
//        }
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
