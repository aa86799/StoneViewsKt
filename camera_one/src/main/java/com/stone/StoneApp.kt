package com.stone

import android.app.Application

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/1/4 23:28
 */
internal class StoneApp: Application() {

    companion object {
        lateinit var instance:  StoneApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}