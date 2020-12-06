package com.stone.stoneviewskt.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.stone.stoneviewskt.StoneApplication

/**
 * desc:    Bitmap工具类：原生图片压缩；
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/9 20:18
 */
class BitmapUtil {

    fun resize(id: Int, dstW: Int, dstH: Int, hasAlpha: Boolean, reusable: Bitmap): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(StoneApplication.instance.resources, id, options)
        val w = options.outWidth
        val h = options.outHeight
        options.inSampleSize = calculateInSampleSize(w, h, dstW, dstH)
        if (!hasAlpha) {//没有alpha通道，设置config
            options.inPreferredConfig = Bitmap.Config.RGB_565
        }
        options.inJustDecodeBounds = false
        //复用
        options.inMutable = true
        options.inBitmap = reusable
        return BitmapFactory.decodeResource(StoneApplication.instance.resources, id, options)

    }

    //图片缩放
    fun resize(id: Int, dstW: Int, dstH: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(StoneApplication.instance.resources, id, options)

        val w = options.outWidth
        val h = options.outHeight
        options.inSampleSize = calculateInSampleSize(w, h, dstW, dstH)
        options.inJustDecodeBounds = false
//        options.inPreferredConfig = Bitmap.Config.RGB_565  //无 alpha 通道
        return BitmapFactory.decodeResource(StoneApplication.instance.resources, id, options)
    }

    private fun calculateInSampleSize(w: Int, h: Int, dstW: Int, dstH: Int): Int {
        var inSampleSize = 1 //Bitmap.Option.inSampleSize 必须是 2的n次方
        if (w > dstW || h > dstH) {
            inSampleSize = inSampleSize shl 1 // << 1
            while (w / inSampleSize > dstW || h / inSampleSize > dstH) {
                inSampleSize = inSampleSize shl 1 // << 1
            }
        }
        return inSampleSize
    }
}