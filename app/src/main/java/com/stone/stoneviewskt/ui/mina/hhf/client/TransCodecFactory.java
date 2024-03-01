package com.stone.stoneviewskt.ui.mina.hhf.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class TransCodecFactory implements ProtocolCodecFactory{
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return new TransDataEncoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return new TransDataDecoder();
    }
}
