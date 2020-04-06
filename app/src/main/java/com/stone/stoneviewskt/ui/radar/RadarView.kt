package com.stone.stoneviewskt.ui.radar

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 22/07/2017 11 37
 */

class RadarView: View {

    private var mRadius = 0f
    private var mPaint = Paint()
    private lateinit var mShader: SweepGradient
    private var mRx = 0f
    private var mRy = 0f
    private val mRingCount = 4
    private var mRect: RectF = RectF()
    private var mSweepStartAngle = 0f
    private var mSweepAngle = 120f
    private var mSweepAngleSpeed = 10f
    private var mIsStartSweep = false

    companion object {
        private const val STATE_START = 1
        private const val STATE_STOP = 2
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                STATE_START -> {
                    mSweepStartAngle += mSweepAngleSpeed
                    invalidate()
                    sendMessageDelayed(Message.obtain(this, STATE_START), 30)
                }

                STATE_STOP -> {
                    removeCallbacksAndMessages(null)
                }
            }
        }
    }

    constructor(context: Context) : super(context) {
        initParams(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initParams(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initParams(context, attrs, defStyleAttr)
    }

    private fun initParams(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val w = resources.displayMetrics.widthPixels
        val h = resources.displayMetrics.heightPixels
        val min = Math.min(w, h)
        mRadius = (min / 2 - 50).toFloat()
        mRx = (w / 2).toFloat()
        mRy = (h / 2).toFloat()

        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 5f

        mRect = RectF(mRx - mRadius, mRy - mRadius, mRx + mRadius, mRy + mRadius)

//        mShader = SweepGradient(mRx, mRy, 0xddff00f0.toInt(), 0xffabc777.toInt())
        mShader = SweepGradient(mRx, mRy,
            intArrayOf(0xee191970.toInt(), 0xddff000f.toInt(), 0xddff00f0.toInt(), 0xaaff0000.toInt()),
            floatArrayOf(0.0f, 0.1f, 0.6f, 0.98f))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = Color.BLUE
        mPaint.shader = null
        canvas.drawCircle(mRx, mRy, mRadius, mPaint)

        mPaint.color = Color.parseColor("#b7b7b7")
        mPaint.style = Paint.Style.STROKE
        canvas.drawLine(mRx - mRadius, mRy, mRadius + mRx, mRy, mPaint)
        canvas.drawLine(mRx, mRy - mRadius, mRx, mRy + mRadius, mPaint)
        val eachRingUnit = mRadius / mRingCount
        var curRingRadius: Float = 0f
        (1 until mRingCount).forEach { _ ->
            curRingRadius += eachRingUnit
            canvas.drawCircle(mRx, mRy, curRingRadius, mPaint)
        }

        mPaint.shader = mShader
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = Color.parseColor("#ccffffff")
        canvas.drawArc(mRect, mSweepStartAngle, mSweepAngle, true, mPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mIsStartSweep = !mIsStartSweep
                if (mIsStartSweep) {
                    Message.obtain(mHandler, STATE_START).sendToTarget()
                } else {
                    Message.obtain(mHandler, STATE_STOP).sendToTarget()
                }
                return true
            }
        }

        return super.onTouchEvent(event)
    }
}