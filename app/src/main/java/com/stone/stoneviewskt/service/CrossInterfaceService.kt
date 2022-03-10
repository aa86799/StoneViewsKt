package com.stone.stoneviewskt.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class CrossInterfaceService: Service() {

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
}