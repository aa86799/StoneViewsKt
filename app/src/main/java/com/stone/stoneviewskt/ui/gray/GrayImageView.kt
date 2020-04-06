package com.stone.stoneviewskt.ui.gray

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/**
 * desc:    App 黑白化实现探索
 *              https://mp.weixin.qq.com/s/8fTWLYaPhi0to47EUmFd7A
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 19:15
 */
class GrayImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int): AppCompatImageView(context, attrs, defStyleAttr) {

    constructor(context: Context,  attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val mPaint = Paint()

    init {
        val cm = ColorMatrix()
        cm.setSaturation(0f) //饱和度为0，即灰度
        mPaint.colorFilter = ColorMatrixColorFilter(cm)
    }

    override fun draw(canvas: Canvas) {
        //首个图层或者说 底部图层 应用 mPaint
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.draw(canvas)
        canvas.restore()
    }
}