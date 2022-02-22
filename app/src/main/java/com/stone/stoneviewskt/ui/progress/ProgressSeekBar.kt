package com.stone.stoneviewskt.ui.progress

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 2020/11/23 19:39
 */

class ProgressSeekBar constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatSeekBar(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private val mPaint: Paint
    private val mProgressTextRect: Rect
    private val mRectF: RectF
    private var mProgressText: String = ""

    init {
        mPaint = TextPaint()
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        mPaint.textSize = this.thumb.intrinsicHeight.toFloat() / 2 - 2
        mProgressTextRect = Rect()
        mRectF = RectF()
        // 如果不设置padding，当滑动到最左边或最右边时，滑块会显示不全
        setPadding(this.thumb.intrinsicWidth / 2, 0, this.thumb.intrinsicWidth / 2, 0)

        setProgressText("")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.getTextBounds(mProgressText, 0, mProgressText.length, mProgressTextRect)

        val progressRatio: Float = progress.toFloat() / max.toFloat()
        // 计算baseline
        val fontMetrics = mPaint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        mRectF.left = (width - paddingEnd - paddingStart) * progressRatio - mPaint.measureText(mProgressText) / 2
        mRectF.top = (measuredHeight - height) / 2f
        mRectF.right = mRectF.left + thumb.intrinsicWidth
        mRectF.bottom = (measuredHeight + height) / 2f
        val baseline = mRectF.centerY() + distance
        canvas.drawText(mProgressText, mRectF.centerX(), baseline, mPaint)
    }

    fun setProgressText(text: String) {
        mProgressText = text
        if (mProgressText.isEmpty()) {
            mProgressText = progress.toString()
        }
    }
}
