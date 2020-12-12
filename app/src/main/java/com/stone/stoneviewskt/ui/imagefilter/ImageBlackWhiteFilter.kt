package com.stone.stoneviewskt.ui.imagefilter

import android.graphics.Bitmap

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/4 11:49
 */
class ImageBlackWhiteFilter: IImageFilter {

    private external fun blackWhiteFilter(bm: Bitmap)

    override fun process(bitmap: Bitmap): Bitmap? {
        blackWhiteFilter(bitmap)
        return bitmap
    }
}