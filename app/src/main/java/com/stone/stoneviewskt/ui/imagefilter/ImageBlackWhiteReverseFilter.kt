package com.stone.stoneviewskt.ui.imagefilter

import android.graphics.Bitmap

/**
 * desc:    黑白逆转
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/4 11:49
 */
class ImageBlackWhiteReverseFilter: IImageFilter {

    private external fun blackWhiteReverseFilter(bm: Bitmap)

    override fun process(bitmap: Bitmap): Bitmap? {
        blackWhiteReverseFilter(bitmap)
        return bitmap
    }
}