package com.stone.stoneviewskt.ui.mina.hhf.manager;

import android.util.Log;

import org.apache.mina.core.session.IoSession;

/**
 * Created by huanghongfa on 2017/7/28.
 */

public class SessionManager {

    private static SessionManager instance;

    private IoSession mSession;

    public static SessionManager getInstance() {
        if (null == instance) {
            instance = new SessionManager();
        }
        return instance;
    }

    private SessionManager() {

    }

    public void setSession(IoSession session) {
        this.mSession = session;
    }

    public void writeToServer(Object msg) {
        if (mSession != null) {
            Log.e("tag", "客户端准备发送消息");
            mSession.write(msg);
        }
    }

    public void closeSession() {
        if (mSession != null && mSession.isConnected()) {
            mSession.closeNow();
            mSession = null;
        }
    }
}
