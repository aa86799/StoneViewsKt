package com.stone.stoneviewskt.ui.imagefilter.filters

import android.graphics.Bitmap

/**
 * desc:    Stucki dithering  ：Stucki扩散抖动算法
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/12 14:08
 */
class ImageStuckiFilter: IImageFilter {

    private external fun stuckiFilter(bm: Bitmap)

    override fun process(bitmap: Bitmap): Bitmap? {
        stuckiFilter(bitmap)
        return bitmap
    }
}