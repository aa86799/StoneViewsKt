package com.stone.stoneviewskt.ui.imagefilter.filters

import android.graphics.Bitmap

/**
 * desc:    Floyd Steinberg dithering  ： Floyd-Steinberg扩散抖动算法
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/4 11:49
 */
class ImageFloydFilter: IImageFilter {

    private external fun floydFilter(bm: Bitmap)

    override fun process(bitmap: Bitmap): Bitmap? {
        floydFilter(bitmap)
        return bitmap
    }
}