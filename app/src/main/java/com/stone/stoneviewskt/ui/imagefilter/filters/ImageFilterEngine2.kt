package com.stone.stoneviewskt.ui.imagefilter.filters

import android.graphics.Bitmap

/**
 * desc:    使用 委托 类
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/27 04:48
 */
class ImageFilterEngine2(filter: IImageFilter): IImageFilter by filter {

    fun processBitmap(bitmap: Bitmap): Bitmap? {
//        val bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val bmp = bitmap.copy(bitmap.config, true)
        this.process(bmp)
        return bmp
    }
}