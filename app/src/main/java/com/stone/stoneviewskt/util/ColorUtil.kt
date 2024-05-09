package com.stone.stoneviewskt.util

import android.graphics.Color
import java.util.Random

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 09/06/2017 23 37
 */

/**
 * 随机颜色
 * @return
 */
fun getRandomArgbColor(): Int {
    val sb = StringBuilder()
    val random = Random()
    var temp: String
    //argb四个区域
    for (i in 0..3) {
        temp = Integer.toHexString(random.nextInt(0xFF))

        if (temp.length == 1) {
            temp = "0$temp"
        }
        sb.append(temp)
    }
    return Color.parseColor("#$sb")
}

fun getRandomRGBColor(): Int {
    val sb = StringBuilder()
    val random = Random()
    var temp: String
    //rgb四个区域
    for (i in 0..2) {
        temp = Integer.toHexString(random.nextInt(0xFF))

        if (temp.length == 1) {
            temp = "0$temp"
        }
        sb.append(temp)
    }
    return Color.parseColor("#$sb")
}
