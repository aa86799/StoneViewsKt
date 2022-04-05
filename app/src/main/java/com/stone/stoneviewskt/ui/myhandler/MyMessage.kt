package com.stone.stoneviewskt.ui.myhandler

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/3/27 11:07
 */
data class MyMessage(
    val what: Int,
    val msg: String,
    var target: MyHandler
)