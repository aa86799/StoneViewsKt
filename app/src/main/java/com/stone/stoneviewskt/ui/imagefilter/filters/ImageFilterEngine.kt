package com.stone.stoneviewskt.ui.imagefilter.filters

import android.graphics.Bitmap

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/4 11:38
 */
object ImageFilterEngine {

    fun blackWhiteImage(bitmap: Bitmap): Bitmap? {
        return processBitmap(bitmap, ImageBlackWhiteFilter())
    }

    fun blackWhiteReverseImage(bitmap: Bitmap): Bitmap? {
        return processBitmap(bitmap, ImageBlackWhiteReverseFilter())
    }

    fun floydImage(bitmap: Bitmap): Bitmap? {
        return processBitmap(bitmap, ImageFloydFilter())
    }

    fun stuckiImage(bitmap: Bitmap): Bitmap? {
        return processBitmap(bitmap, ImageStuckiFilter())
    }

    private fun processBitmap(bitmap: Bitmap, filter: IImageFilter): Bitmap? {
//        val bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val bmp = bitmap.copy(bitmap.config!!, true)
        filter.process(bmp)
        return bmp
    }
}