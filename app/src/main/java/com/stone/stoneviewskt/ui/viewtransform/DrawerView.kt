package com.stone.stoneviewskt.ui.viewtransform

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.stone.stoneviewskt.util.dp

class DrawerView: View {

    private val mPaint = Paint()
    private val mCircleRadius = 20.dp
    private val mPoints = mutableListOf<PointF>()
    private val mColors by lazy {
        val list = mutableListOf<Int>()
        list.add(Color.BLACK)
        list.add(Color.BLUE)
        list.add(Color.GREEN)
        list.add(Color.GRAY)
        list
    }

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.strokeWidth = 2.dp.toFloat()
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
    }

    fun drawPoints(list: List<PointF>) {
        mPoints.clear()
        mPoints.addAll(list)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        mPoints.forEachIndexed { index, pointF ->
            mPaint.color = mColors[index]
            canvas.drawCircle(pointF.x, pointF.y, mCircleRadius.toFloat(), mPaint)
        }
        if (mPoints.size == 4) {
            mPaint.color = Color.parseColor("#c0ff00ff")
//            canvas.drawRect(mPoints[0].x, mPoints[0].y, mPoints[3].x, mPoints[3].y, mPaint)
            canvas.drawLine(mPoints[0].x, mPoints[0].y, mPoints[1].x, mPoints[1].y, mPaint)
            canvas.drawLine(mPoints[0].x, mPoints[0].y, mPoints[2].x, mPoints[2].y, mPaint)
            canvas.drawLine(mPoints[1].x, mPoints[1].y, mPoints[3].x, mPoints[3].y, mPaint)
            canvas.drawLine(mPoints[2].x, mPoints[2].y, mPoints[3].x, mPoints[3].y, mPaint)
        }
    }
}