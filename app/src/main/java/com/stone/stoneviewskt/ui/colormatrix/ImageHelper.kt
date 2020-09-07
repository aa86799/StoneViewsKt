package com.stone.stoneviewskt.ui.colormatrix

import android.graphics.*

object ImageHelper {


    //设置色调 饱和度 亮度
    fun handleImageEffect(bm: Bitmap, hue: Float, saturation: Float, lum: Float): Bitmap {
        val bmp = Bitmap.createBitmap(bm.width, bm.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG) //paint 加抗锯齿

        val hueMatrix = ColorMatrix()
        //设置色调/色相
        hueMatrix.setRotate(0, hue)
        hueMatrix.setRotate(1, hue)
        hueMatrix.setRotate(2, hue)

        //设置饱和度
        val saturationMatrix = ColorMatrix()
        saturationMatrix.setSaturation(saturation)

        //设置亮度
        val lumMatrix = ColorMatrix()
        lumMatrix.setScale(lum, lum, lum, 1f)

        //联结 上面三个 ColorMatrix
        val imageMatrix = ColorMatrix()
        imageMatrix.postConcat(hueMatrix)
        imageMatrix.postConcat(saturationMatrix)
        imageMatrix.postConcat(lumMatrix)

        paint.colorFilter = ColorMatrixColorFilter(imageMatrix)
        canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG) //canvas加抗锯齿
        canvas.drawBitmap(bm, 0f, 0f, paint)
        return bmp
    }

    /**
     *  底片效果，黑白底片。获取像素点颜色后，用255 分别减去 相应的 rgb 值
     *   bitmap.getPixels(colors...); 对像素点颜色数组中的每个元素，做rgb通道的变换; bitmap.setPixels(colors...)
     */
    fun handleImageNegative(bm: Bitmap): Bitmap {
        val width = bm.width
        val height = bm.height
        var color: Int
        var r: Int
        var g: Int
        var b: Int
        var a: Int
        val bmp = Bitmap.createBitmap(
                width, height, Bitmap.Config.ARGB_8888
        )
        val oldPx = IntArray(width * height)
        val newPx = IntArray(width * height)
        bm.getPixels(oldPx, 0, width, 0, 0, width, height)
        for (i in 0 until width * height) {
            color = oldPx[i]
            r = Color.red(color)
            g = Color.green(color)
            b = Color.blue(color)
            a = Color.alpha(color)
            r = 255 - r
            g = 255 - g
            b = 255 - b
            if (r > 255) {
                r = 255
            } else if (r < 0) {
                r = 0
            }
            if (g > 255) {
                g = 255
            } else if (g < 0) {
                g = 0
            }
            if (b > 255) {
                b = 255
            } else if (b < 0) {
                b = 0
            }
            newPx[i] = Color.argb(a, r, g, b)
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height)
        return bmp
    }

    /**
     *  老照片效果
     *  bitmap.getPixels(colors...); 对像素点颜色数组中的每个元素，做rgb通道的变换; bitmap.setPixels(colors...)
     */
    fun handleImagePixelsOldPhoto(bm: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(
                bm.width, bm.height,
                Bitmap.Config.ARGB_8888
        )
        val width = bm.width
        val height = bm.height
        var color = 0
        var r: Int
        var g: Int
        var b: Int
        var a: Int
        var r1: Int
        var g1: Int
        var b1: Int
        val oldPx = IntArray(width * height)
        val newPx = IntArray(width * height)
        bm.getPixels(oldPx, 0, bm.width, 0, 0, width, height)
        for (i in 0 until width * height) {
            color = oldPx[i]
            a = Color.alpha(color)
            r = Color.red(color)
            g = Color.green(color)
            b = Color.blue(color)
            r1 = (0.393 * r + 0.769 * g + 0.189 * b).toInt()
            g1 = (0.349 * r + 0.686 * g + 0.168 * b).toInt()
            b1 = (0.272 * r + 0.534 * g + 0.131 * b).toInt()
            if (r1 > 255) {
                r1 = 255
            }
            if (g1 > 255) {
                g1 = 255
            }
            if (b1 > 255) {
                b1 = 255
            }
            newPx[i] = Color.argb(a, r1, g1, b1)
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height)
        return bmp
    }

    /**
     * 浮雕效果.
     * bitmap.getPixels(colors...); 对像素点颜色数组中的每个元素，做rgb通道的变换; bitmap.setPixels(colors...)
     */
    fun handleImagePixelsRelief(bm: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(
                bm.width, bm.height,
                Bitmap.Config.ARGB_8888
        )
        val width = bm.width
        val height = bm.height
        var color = 0
        var colorBefore = 0
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        var r1: Int
        var g1: Int
        var b1: Int
        val oldPx = IntArray(width * height)
        val newPx = IntArray(width * height)
        bm.getPixels(oldPx, 0, bm.width, 0, 0, width, height)
        for (i in 1 until width * height) {
            colorBefore = oldPx[i - 1] //前一个像素
            a = Color.alpha(colorBefore)
            r = Color.red(colorBefore)
            g = Color.green(colorBefore)
            b = Color.blue(colorBefore)
            color = oldPx[i] //当前像素
            r1 = Color.red(color)
            g1 = Color.green(color)
            b1 = Color.blue(color)
            r = r - r1 + 127
            g = g - g1 + 127
            b = b - b1 + 127
            if (r > 255) {
                r = 255
            }
            if (g > 255) {
                g = 255
            }
            if (b > 255) {
                b = 255
            }
            newPx[i] = Color.argb(a, r, g, b)
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height)
        return bmp
    }
}