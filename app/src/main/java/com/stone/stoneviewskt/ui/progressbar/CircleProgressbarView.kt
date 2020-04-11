package com.stone.stoneviewskt.ui.progressbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * desc   : 圆环进度。自适应text宽度
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/9/14 09 10
 */
class CircleProgressbarView : View {
    private var mProgress = 0f
    private var mBarColor = 0
    private var mTextColor = 0
    private val mPaint = Paint()

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        mBarColor = Color.parseColor("#abc777")
        mTextColor = Color.parseColor("#ff0000")
    }

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context): this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setProgress(count: Float) {
        mProgress = count
        if (mProgress > 100) mProgress = 100f //最大进度100
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val wh = min(width, height).toFloat()
        val w = wh
        val h = wh
        val strokeWidth = 20
        val radius = w / 2 - strokeWidth / 2 //大圆 半径
        val barBitmap = Bitmap.createBitmap(w.toInt(), h.toInt(), Bitmap.Config.ARGB_8888)

        mPaint.isAntiAlias = true //抗锯齿
        mPaint.strokeWidth = strokeWidth.toFloat() //描边宽
        mPaint.isDither = true //防抖动

        val circleCanvas = Canvas(barBitmap)
        mPaint.color = Color.argb(0x11, 0xcc, 0x0a, 0xf0)
//        mPaint.color = Color.BLACK
        val rect = RectF(0f, 0f, w, h)
        circleCanvas.drawRect(rect, mPaint)

        /* 内圆 */
        mPaint.color = Color.CYAN
        mPaint.shader = LinearGradient(0f, 0f, w, h, Color.RED, Color.GREEN, Shader.TileMode.CLAMP)
        circleCanvas.drawCircle(w / 2.toFloat(), h / 2.toFloat(), radius - strokeWidth / 2.toFloat(), mPaint)
        mPaint.shader = null

        /* 外圆 */
        mPaint.color = mBarColor
        mPaint.style = Paint.Style.STROKE
        circleCanvas.drawCircle(w / 2.toFloat(), h / 2.toFloat(), radius, mPaint)

        /* 内外圆之间的圆环 */
        mPaint.shader = LinearGradient(0f, 0f, w, h, intArrayOf(Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED), floatArrayOf(0.2f, 0.5f, 0.7f, 1.0f), Shader.TileMode.CLAMP)
        val cur = mProgress * 360 * 0.01f
        circleCanvas.drawArc(RectF((strokeWidth / 2).toFloat(), (strokeWidth / 2).toFloat(), w - strokeWidth / 2, h - strokeWidth / 2),
                360 - 45.toFloat(), cur, false, mPaint)
        mPaint.shader = null

        /* 文本 */
        mPaint.color = mTextColor
        mPaint.textAlign = Paint.Align.CENTER //Left is default
        val percent = "${String.format("%.2f", mProgress)}%"
        calcTextSize(mPaint, w, percent)
        val fm = mPaint.fontMetrics
        val baseline = (h - fm.top - fm.bottom) / 2
        mPaint.style = Paint.Style.FILL
        circleCanvas.drawText(percent, w / 2, baseline, mPaint)

        canvas.drawBitmap(barBitmap, 0f, 0f, mPaint)
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
        while (width < max * 3 / 5) {
            paint.textSize += 5
            width = paint.measureText(text)
        }
        paint.textSize -= 5 //防循环后过大
    }
}