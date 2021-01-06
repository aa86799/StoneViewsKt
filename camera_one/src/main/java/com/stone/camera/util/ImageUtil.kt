package com.stone.camera.util

import android.graphics.Bitmap
import android.graphics.Matrix

object ImageUtil {
    /**
     * 旋转Bitmap
     */
    @JvmStatic
    fun getRotateBitmap(bm: Bitmap, rotateDegree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotateDegree)
        return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, false)
    }
}