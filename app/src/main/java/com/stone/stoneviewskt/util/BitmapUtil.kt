package com.stone.stoneviewskt.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import com.stone.stoneviewskt.StoneApplication
import java.io.BufferedInputStream

/**
 * desc:    Bitmap工具类：原生图片压缩；
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/9 20:18
 */
object BitmapUtil {

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
        var inSampleSize = 1 //Bitmap.Option.inSampleSize 必须是 2的n次方才有效；累加后系统会选最接近的2^n
        if (w > dstW || h > dstH) {
            inSampleSize += 1
            while (w / inSampleSize > dstW || h / inSampleSize > dstH) {
                inSampleSize += 1
            }
        }
        return inSampleSize
    }

    //stream读取的 bitmap，后置拍的照，加载出来的方向 顺时针被转了90度
    fun loadBitmapFromUri(uri: Uri, dstW: Int, dstH: Int, isMutable: Boolean = false, reusable: Bitmap? = null): Bitmap? {
//        val parcelFileDescriptor = StoneApplication.instance.contentResolver?.openFileDescriptor(uri, "r")
        StoneApplication.instance.contentResolver?.openInputStream(uri)?.use {
            BufferedInputStream(it).use { bis ->
                bis.mark(it.available() + 1)
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                val bm = BitmapFactory.decodeStream(bis, null, options)  //流decode一次就无法再decode了
                val w = options.outWidth
                val h = options.outHeight

                bis.reset()
                options.inSampleSize = calculateInSampleSize(w, h, dstW, dstH)

                options.inJustDecodeBounds = false
                options.inPreferredConfig = Bitmap.Config.RGB_565
                val r = StoneApplication.instance.resources
                val targetDensity = r.displayMetrics.densityDpi
//                options.inTargetDensity = targetDensity
//                options.inDensity = (targetDensity * r.displayMetrics.density).toInt()

                options.inMutable = isMutable
                options.inBitmap = reusable ?: bm

                return BitmapFactory.decodeStream(bis, null, options)
            }
            // 从uri 创建inputStream； 再从stream创建drawable
//            return Drawable.createFromResourceStream(it, DocumentFile.fromSingleUri(StoneApplication.instance, uri)?.name)
        }
        return null
    }

    fun loadDrawableFromUri(uri: Uri, dstW: Int, dstH: Int, isMutable: Boolean = false, reusable: Bitmap? = null): Drawable? {
        val bm = loadBitmapFromUri(uri, dstW, dstH, isMutable, reusable)
        bm ?: return null
        return BitmapDrawable(StoneApplication.instance.resources, bm)
    }
}