package com.stone.stoneviewskt.ui.progressbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.stone.stoneviewskt.util.loge
import java.util.*

/**
 * 斜线进度条，与随机位置绘制小点
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/10/22 11 11
 */
class ObliqueProgressbarView constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mPaint: Paint = Paint()
    private var mProgress = 0f //最大1.0

    init {
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mProgress == 0f) return

        //碎片雨
        mPaint.color = Color.parseColor("#a96ecb")
        mPaint.strokeWidth = 3f
        val random = Random()
        var sx: Int
        var sy: Int
        for (i in 0..999) {
            sx = random.nextInt(width + 10)
            sy = random.nextInt(height + 10)
            //            canvas.drawLine(sx, sy, sx+random.nextInt(5), sy+random.nextInt(5), mPaint);
            canvas.drawCircle(sx.toFloat(), sy.toFloat(), random.nextInt(5) + 1.toFloat(), mPaint)
        }

        //进度
        mPaint.strokeWidth = 15f
        val x = mProgress * width
        var i = 0
        while (i - 100 < x) {
            mPaint.color = Color.parseColor("#1d00ff00")
            canvas.drawLine(i - 100.toFloat(), -10f, i - 100 + 100.toFloat(), height + 10.toFloat(), mPaint)
            i += 30
        }

        mPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 40f, context.resources.displayMetrics)
        mPaint.typeface = Typeface.SERIF
        mPaint.textAlign = Paint.Align.CENTER //会将text 水平绘制在 x、y的中间
        val fm = mPaint.fontMetrics
        val baseline = (height - fm.top - fm.bottom) / 2

//        mPaint.color = Color.RED
//        canvas.drawRect(RectF(0f, 0f, 150f, baseline), mPaint)
//        mPaint.color = Color.BLUE
//        canvas.drawRect(RectF(0f, baseline, 150f, height.toFloat()), mPaint)

        mPaint.color = Color.CYAN
        if (x >= width) {
            canvas.drawText("Progress: 100%", width / 2f, baseline, mPaint)
        } else {
            val progressText = "Progress: ${(mProgress * 100).toInt()}%"
            canvas.drawText(progressText, width / 2f, baseline, mPaint) //配合 Paint.Align.CENTER， 与这的 x 、y 值 ，使文本 上下与左右都居中。
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        loge(event.x.toString())
        loge(event.y.toString())
        return true
    }

    fun setProgress(progress: Float) {
        mProgress = progress
        invalidate()
    }

}