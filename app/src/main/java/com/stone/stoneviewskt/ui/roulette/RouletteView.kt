package com.stone.stoneviewskt.ui.roulette

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.util.getRandomColor
import com.stone.stoneviewskt.util.loge
import java.util.*
import kotlin.math.min

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 09/06/2017 23 28
 */
class RouletteView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr), Runnable {

    private val mPaint: Paint
    private var mThread: Thread? = null
    private val mScreenWith: Int = resources.displayMetrics.widthPixels
    private val mScreenHeight: Int = resources.displayMetrics.heightPixels
    var mRadius: Int = 100
    var mPart: Int = 1 //等分数
        set(value) {
            field = value
            for (i in 0 until value) {
                if (i == 0)
                    mColorList.add(Color.RED)
                else
                    mColorList.add(getRandomColor())
                mPathList.clear()
                mPathList.add(Path())
                loge("color list " + mColorList.size)
            }
        }
    private var mMin: Int = 200
    private var mCenterX: Int = 300
    private var mCenterY: Int = 300
    private val outRectF = RectF()
    private var mAngle: Float = 0.toFloat()
    private val mColorList: MutableList<Int> = mutableListOf()
    private var mRunFlag: Boolean = false
    private var mRunEnd: Boolean = false
    private var mStartRunTime: Long = 0
    private var mEndRunTime: Long = 0
    private var mAcceleration = 5
    private var mMaxAcceleration = 50
    private var mPathList: MutableList<Path> = mutableListOf()
    private var mOnPartClickListener: ((index: Int) -> Unit)? = null

    interface OnPartClickListener {
        fun onPartClick(index: Int)
    }

    fun setOnPartClickListener(block: (index: Int) -> Unit) {
        this.mOnPartClickListener = block
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        mMin = min(mScreenWith, mScreenHeight)
        mRadius = mMin / 2  //使用小的一边作为半径默认值

        this.mPaint = Paint()
        this.mPaint.isAntiAlias = true //设置画笔无锯齿
        this.mPaint.strokeWidth = 6f
        this.mPaint.style = Paint.Style.FILL
        this.mPaint.textSize = 30f

        isFocusable = true //设置焦点

        if (attrs != null) {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                mRadius.toFloat(),
                resources.displayMetrics
            )
            val array = context.obtainStyledAttributes(attrs, R.styleable.RouletteView)
            mRadius = array.getDimension(R.styleable.RouletteView_radius, mRadius.toFloat()).toInt()
            mPart = array.getInt(R.styleable.RouletteView_part, mPart)
            array.recycle()
        }
        mPathList.clear()
        for (i in 0 until mPart) {
            if (i == 0)
                mColorList.add(Color.RED)
            else
                mColorList.add(getRandomColor())
            mPathList.add(Path())
            loge("color list " + mColorList.size)
        }
    }

    fun startRotate() {
        mAngle = 0f
        mRunFlag = true
        mRunEnd = false
        mThread = Thread(this)
        mThread?.start()
        mStartRunTime = System.currentTimeMillis()
    }

    fun stopRotate() {
        mRunEnd = true//要停止run
        mEndRunTime = System.currentTimeMillis()
    }

    override fun run() {
        while (mRunFlag) {
            val start = System.currentTimeMillis()
            logic()
            postInvalidate()
            val end = System.currentTimeMillis()
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start))
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mMin, mMin)
    }

    override fun onDraw(canvas: Canvas) {
        //画布旋转
//        canvas.rotate(mAngle, mCenterX.toFloat(), mCenterY.toFloat())

//        canvas.drawPaint(this.mPaint)

        //绘制在横向居中 顶部 位置
        mCenterX = mMin / 2
        mCenterY = mRadius
        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mRadius.toFloat(), this.mPaint)

        //整个圆的外矩形   原平稳的矩形旋转后，其所在范围的外矩形大可能会变大，因为屏幕上还是以 ltrb来 计算绘制
        outRectF.set(
            (mCenterX - mRadius).toFloat(),
            0f,
            (mCenterX + mRadius).toFloat(),
            (2 * mRadius).toFloat()
        )

        //等分圆 绘制扇形
//        for (i in 0 until mPart) {
//            this.mPaint.color = mColorList[i]
////            outRectF 穿过中心点的横轴，右方向是0度
////            canvas.drawArc(outRectF, (360 / mPart * i).toFloat(), (360 / mPart).toFloat(), false, this.mPaint)
//            canvas.drawArc(outRectF, (360 / mPart * i).toFloat(), (360 / mPart).toFloat(), true, this.mPaint)
//        }

        //使用 path等分圆，绘制扇形。 方便后续判断 点击的 part index.
        for (i in 0 until mPart) {
            mPathList[i].reset()
            this.mPaint.color = mColorList[i]
            mPathList[i].apply {
                addArc(
                    outRectF,
                    360 / mPart * i + mAngle % 360,
                    (360 / mPart).toFloat()
                )
            }
            mPathList[i].lineTo(mCenterX.toFloat(), mCenterY.toFloat())
            mPathList[i].close()
            canvas.drawPath(mPathList[i], mPaint)

//            canvas.drawText("$i", mPathList[i].)
        }
    }

    private fun logic() {
        if (!mRunEnd) {
            when {
                System.currentTimeMillis() - mStartRunTime > 1500 -> {
                    mAngle += mMaxAcceleration
                }
                System.currentTimeMillis() - mStartRunTime > 500 -> {
                    mAngle += mAcceleration * 2
                }
                System.currentTimeMillis() - mStartRunTime > 50 -> {
                    mAngle += mAcceleration
                }
            }
        } else {
            val random = Random()
            when {
                System.currentTimeMillis() - mEndRunTime > 1500 -> {
                    mAngle += 0f
                    mRunFlag = false
                }
                System.currentTimeMillis() - mEndRunTime > 1000 -> {
                    mAngle += mAcceleration * 2 - random.nextInt(5)
                }
                System.currentTimeMillis() - mEndRunTime > 500 -> {
                    mAngle += mAcceleration * 2 + random.nextInt(5)
                }
                System.currentTimeMillis() - mEndRunTime > 50 -> {
                    mAngle += mMaxAcceleration - 10
                }
            }
        }

    }

    fun getRunStats(): Boolean {
        return mRunFlag
    }

    fun setPart(part: Int) {
        this.mPart = part
        invalidate()
    }

    private fun getPointPart(x: Float, y: Float): Int {
        var resIndex: Int = -1
        mPathList.forEachIndexed { index, path ->
            if (pointIsInPath(x, y, path)) {
                resIndex = index
            }
        }
        return resIndex
    }

    private fun pointIsInPath(x: Float, y: Float, path: Path): Boolean {
        val bounds = RectF()
        path.computeBounds(bounds, true)
        val region = Region()
        region.setPath(
            path,
            Region(
                Rect(
                    bounds.left.toInt(),
                    bounds.top.toInt(),
                    bounds.right.toInt(),
                    bounds.bottom.toInt()
                )
            )
        )

        return region.contains(x.toInt(), y.toInt())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                stopRotate()
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_UP -> {
                val partIndex = getPointPart(event.x, event.y)
//                when (partIndex) {
//                    -1 -> {
//                        showShort("未点击扇形区")
//                    }
//                    else -> {
//                        showShort("扇形区索引=$partIndex")
//                    }
//                }
                mOnPartClickListener?.invoke(partIndex)
            }
        }
        return true
    }

}
