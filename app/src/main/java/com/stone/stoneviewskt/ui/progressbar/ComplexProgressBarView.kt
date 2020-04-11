package com.stone.stoneviewskt.ui.progressbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min


/**
 * desc:    左边横向圆角进度条，右边文本为  "进度/最大进度"
 * author:  stone
 * email:   aa86799@163.com
 * time:    2019-10-18 15:27
 */
class ComplexProgressBarView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    var mMax = 100
    var mProgress = 95
        set(value) {
            field = value
            invalidate()
        }
    var mBackColor = 0
    var mProgressColor = 0
    var mTextColor = 0
    var mPaint = Paint()
    var mOffset = 10
    var mOvalRadius = 40f
    var mHorizontalBarHeight = 40f

    private var mMaxHW = 0f

    init {
        mBackColor = Color.parseColor("#e6e6e6")
        mProgressColor = Color.parseColor("#0E91F5")
        mTextColor = Color.parseColor("#999999")
        mPaint.color = mBackColor
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.style = Paint.Style.FILL_AND_STROKE
//        setBackgroundColor(Color.BLACK)
    }

    override fun onDraw(canvas: Canvas) {
        mMaxHW = min(measuredHeight, measuredWidth).toFloat()
        canvas.translate(0f, measuredHeight/2 - mMaxHW/2)
        val numMaxWidth = mMaxHW / 3

        val height = min(mHorizontalBarHeight, mMaxHW)
        val text = "$mProgress/$mMax"
        calcTextSize(mPaint, numMaxWidth, text)
        val width = mMaxHW - mOffset - numMaxWidth

        mPaint.color = mBackColor
        canvas.drawOval(RectF(0f, (mMaxHW - height) / 2, mOvalRadius * 2, (mMaxHW + height) / 2), mPaint)
        canvas.drawRect(RectF(mOvalRadius, (mMaxHW - height) / 2, width - mOvalRadius, (mMaxHW + height) / 2), mPaint)
        canvas.drawOval(RectF(width - mOvalRadius * 2, (mMaxHW - height) / 2, width, (mMaxHW + height) / 2), mPaint)

        val scale = mProgress / mMax.toFloat()
        mPaint.color = mProgressColor
        var right = if (scale == 0.0f) {
            0.0f
        } else {
            width * scale
        }
        if (right < mOvalRadius * 2) right = mOvalRadius * 2
        canvas.drawOval(RectF(0f, (mMaxHW - height) / 2, mOvalRadius * 2, (mMaxHW + height) / 2), mPaint)
        canvas.drawRect(RectF(mOvalRadius, (mMaxHW - height) / 2, right - mOvalRadius, (mMaxHW + height) / 2), mPaint)
        canvas.drawOval(RectF(right - mOvalRadius * 2, (mMaxHW - height) / 2, right, (mMaxHW + height) / 2), mPaint)

        mPaint.color = mTextColor
        //计算baseline
        val fontMetrics = mPaint.fontMetrics
        val baseline = (mMaxHW - fontMetrics.bottom - fontMetrics.top) / 2
        canvas.drawText(text, width + mOffset, baseline, mPaint)
    }

    /**
     * 计算并设置最适合的textSize， 用于适配text宽度
     *
     * @param paint
     * @param max    最大宽度
     */
    private fun calcTextSize(paint: Paint, max: Float, text: String) {
        paint.textSize = 10f
        var width = paint.measureText(text)
        while (width < max) {
            paint.textSize += 5
            width = paint.measureText(text)
        }
        paint.textSize -= 5 //防循环后过大
    }

}