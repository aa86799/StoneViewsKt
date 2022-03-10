package com.stone.stoneviewskt.service.core

import android.annotation.SuppressLint
import android.content.Context
import com.stone.stoneviewskt.service.CesManager

class SuperCross private constructor() {

    private lateinit var mContext: Context
    private val mTypeContent:TypeContent = TypeContent.instance

    companion object {
        @SuppressLint("StaticFieldLeak")
        val instance = Holder.instance
    }

    @SuppressLint("StaticFieldLeak")
    private object Holder {
        val instance = SuperCross()
    }

    fun init(context: Context) {
        mContext = context.applicationContext
    }

    fun register(clz: Class<CesManager>) {
        mTypeContent.register(clz)
    }
}