package com.stone.stoneviewskt.ui.clock

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.abs
import kotlin.math.min

/**
 * 时钟
 * author : stone
 * email  : aa86799@163.com
 * time   : 2016/11/7 17 46
 */
class ClockView(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val mPaint: Paint = Paint()
    private var mW = 0
    private var mH = 0
    private var mCx = 0
    private var mCy = 0
    private var mR = 0
    private var mCount = 0
    private val mPath: Path = Path()
    private val mHandler: Handler = Handler {
        postInvalidate()
        true
    }

    companion object {
        private const val H_POINTER_WIDTH = 15 //时针宽
        private const val M_POINTER_WIDTH = 10 //分针宽
        private const val S_POINTER_WIDTH = 5 //秒针宽
        private const val SCALE_LINE_LENGTH = 50 //刻度线长
        private const val SCALE_LINE_WIDTH = 6 //刻度线宽
        private const val M_S_DEGREES_UNIT = 360 / 60 //分、秒针每个数字走的角度
        private const val H_DEGREES_UNIT = 360 / 12 //时针每个数字走的角度
    }

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mW = width
        mH = height
        mR = min(mW, mH) / 2 - 20
        mCx = mW / 2
        mCy = mH / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val beginTime = SystemClock.uptimeMillis()
        mPaint.color = Color.LTGRAY
        canvas.drawCircle(mCx.toFloat(), mCy.toFloat(), mR.toFloat(), mPaint) //圆表盘
        mPaint.color = Color.BLACK
        canvas.drawCircle(mCx.toFloat(), mCy.toFloat(), mR / 12.toFloat(), mPaint) //圆心

        /*
         * 绘制刻度
         */
        for (i in 0..59) {
            canvas.save()
            canvas.rotate(M_S_DEGREES_UNIT * i.toFloat(), mCx.toFloat(), mCy.toFloat())
            mPaint.strokeWidth = SCALE_LINE_WIDTH.toFloat()
            canvas.drawLine(mCx.toFloat(), mCy - mR.toFloat(), mCx.toFloat(), mCy - mR + SCALE_LINE_LENGTH.toFloat(), mPaint)
            canvas.restore()

            if (i % 5 == 0) {
                mPaint.strokeWidth = SCALE_LINE_WIDTH + 10.toFloat()
                //小时刻度
                canvas.save()
                canvas.rotate(M_S_DEGREES_UNIT * i.toFloat(), mCx.toFloat(), mCy.toFloat())
                canvas.drawLine(mCx.toFloat(), mCy - mR.toFloat(), mCx.toFloat(), mCy - mR + SCALE_LINE_LENGTH + 30.toFloat(), mPaint)

                val num = if (i == 0) 12.toString() else (i / 5).toString()
                /*
                 * 因是旋转画布绘制的，小时数字会有看起来是倒着的。它们顺时针围一圈。
                 * 若想通过简单计算，使数字都正向展示，并不容易。
                 */
                val x = mCx - mPaint.measureText(num) / 2
                val y = mCy - mR + SCALE_LINE_LENGTH + 40.toFloat()
                mPaint.textSize = 50f

                canvas.drawText(num, x, y + abs(mPaint.ascent()), mPaint)
                canvas.restore()
            }
        }
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"))
        val hour = calendar[Calendar.HOUR]
        val minute = calendar[Calendar.MINUTE]
        val second = calendar[Calendar.SECOND]

        /*
         * 分针在最下面 最先绘制
         */
        canvas.save()
        val mDegrees = minute * M_S_DEGREES_UNIT.toFloat()
        canvas.rotate(mDegrees, mCx.toFloat(), mCy.toFloat())
        mPaint.color = Color.GREEN
        mPath.reset()
        mPath.addRect(RectF((mCx - M_POINTER_WIDTH).toFloat(), (mCy - mR / 4 * 3).toFloat(), (mCx + M_POINTER_WIDTH).toFloat(), (mCy + mR / 5).toFloat()), Path.Direction.CW)
        mPath.quadTo(mCx.toFloat(), mCy - mR / 4 * 3 - 40.toFloat(), mCx + M_POINTER_WIDTH.toFloat(), mCy - mR / 4 * 3.toFloat())
        canvas.drawPath(mPath, mPaint)
        canvas.restore()

        /*
         * 时针在中间
         *   分针60分走360度; 时针1小时(即60分)走30度
         *   mDegrees:hDegrees = 360:30 = 12:1
         *   hDegrees = mDegrees / 12; 时针相对于分钟数所要走的角度
         */
        canvas.save()
        canvas.rotate(mDegrees / 12 + hour * H_DEGREES_UNIT, mCx.toFloat(), mCy.toFloat())
        mPaint.color = Color.RED
        mPath.reset()
        mPath.addRect(RectF((mCx - H_POINTER_WIDTH).toFloat(), (mCy - mR / 3 * 2).toFloat(), (mCx + H_POINTER_WIDTH).toFloat(), (mCy + mR / 5).toFloat()), Path.Direction.CW)
        mPath.quadTo(mCx.toFloat(), mCy - mR / 3 * 2 - 30.toFloat(), mCx + H_POINTER_WIDTH.toFloat(), mCy - mR / 3 * 2.toFloat())
        canvas.drawPath(mPath, mPaint)
        canvas.restore()

        /*
         * 秒针在最上面
         */
        canvas.save()
        canvas.rotate(second * M_S_DEGREES_UNIT.toFloat(), mCx.toFloat(), mCy.toFloat())
        mPaint.color = Color.BLACK
        mPath.reset()
        mPath.addRect(RectF((mCx - S_POINTER_WIDTH).toFloat(), (mCy - mR / 5 * 4).toFloat(), (mCx + S_POINTER_WIDTH).toFloat(), (mCy + mR / 5).toFloat()), Path.Direction.CW)
        mPath.quadTo(mCx.toFloat(), mCy - mR / 5 * 4 - 30.toFloat(), mCx + S_POINTER_WIDTH.toFloat(), mCy - mR / 5 * 4.toFloat())
        canvas.drawPath(mPath, mPaint)
        canvas.restore()
        val spanTime = SystemClock.uptimeMillis() - beginTime
        //        System.out.println("间隔时间" + spanTime);
        if (mCount < 5) { //12的位置开始绘制时位置不对，刷新一次后就正常，这里让其快速刷新几次
            mHandler.sendEmptyMessage(0)
            mCount++
        } else mHandler.sendEmptyMessageDelayed(0, 1000 - spanTime)
    }
}