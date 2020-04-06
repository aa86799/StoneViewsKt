package com.stone.stoneviewskt.ui.gray

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * desc   : 灰度化 LinearLayout
 * author : stone
 * email  : aa86799@163.com
 * time   : 2020/4/6 19:25
 */

class GrayLinearLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val mPaint = Paint()

    init {
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        mPaint.colorFilter = ColorMatrixColorFilter(cm)
    }

    override fun draw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.draw(canvas)
        canvas.restore()
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
        canvas.restore()
    }
}