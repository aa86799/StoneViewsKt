package com.stone.stoneviewskt.ui.longimg

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/11/30 10:33
 */
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import java.io.InputStream


/**
 * desc:    长图加载
 * author:  stone
 * email:   aa86799@163.com
 * time:    2020/11/27 12:09
 */
class LongImageView: View, View.OnTouchListener, GestureDetector.OnGestureListener {

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        initViews()
    }

    constructor(context: Context) : this(context, null, 0) {
        initViews()
    }

    //需要显示的区域
    private var mRect: Rect? = null

    //由于需要复用，所有需要option
    private var mOption: BitmapFactory.Options? = null

    //长图需要通过手势滑动来操作
    private var mGestureDetector: GestureDetector? = null

    //滑动帮助类
    private var mScroller: Scroller? = null

    //图片的宽度
    private var mImageWidth = 0

    //图片的高度
    private var mImageHeight = 0

    //控件的宽度
    private var mViewWidth = 0

    //控件的高度
    private var mViewHeight = 0

    //图片缩放因子
    private var mScale = 0f

    //区域解码器
    private var mDecode: BitmapRegionDecoder? = null

    //需要展示的图片，是被复用的
    private var mBitmap: Bitmap? = null


    private fun initViews() {
        mRect = Rect()
        mOption = BitmapFactory.Options()
        mGestureDetector = GestureDetector(context, this)
        setOnTouchListener(this)
        mScroller = Scroller(context)
    }

    /**
     * 由使用者输入一张图片
     *
     * @param input 图片输入流
     */
    fun setImage(input: InputStream) {
        mOption!!.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, mOption)
        mImageWidth = mOption!!.outWidth
        mImageHeight = mOption!!.outHeight
        //开启复用内存
        mOption!!.inMutable = true
        //设置格式，减少内存
        mOption!!.inPreferredConfig = Bitmap.Config.RGB_565
        mOption!!.inJustDecodeBounds = false

        //创建一个区域解码器
        try {
            mDecode = BitmapRegionDecoder.newInstance(input, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //刷新
        invalidate()
    }

    /**
     * 在控件测量中把需要内存区域获取，保存在Rect中
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //获取测量的view的大小
        mViewWidth = measuredWidth
        /*
         * 当父级view高度不定时，这时默认子view会继承父级的特性，高度也不固定。
         * measuredHeight 默认可能是一个小于等于0的值。
         * 所以需要在测量时给定一个具体值。
         */
        mViewHeight = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            resources.displayMetrics.heightPixels
        } else {
            measuredHeight
        }
        mRect!!.top = 0
        mRect!!.left = 0
        mRect!!.right = mImageWidth
        mScale = mViewWidth / mImageWidth.toFloat()
        mRect!!.bottom = (mViewHeight / mScale).toInt()

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mViewHeight, MeasureSpec.EXACTLY))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //如果没有解码器 说明还没有图片，不需要绘制
        if (null == mDecode) {
            return
        }
        mOption!!.inBitmap = mBitmap
        //通过解码器把图解码出来，只加载矩形区域的内容
        mBitmap = mDecode!!.decodeRegion(mRect, mOption)
        //把得到的矩形局域大小的图片通过缩放因子，缩放成控件大小
        val matrix = Matrix()
        matrix.setScale(mScale, mScale)
        canvas.drawBitmap(mBitmap!!, matrix, null)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        //将onTouch事件交给手势处理
        return mGestureDetector!!.onTouchEvent(event)
    }

    /**
     * 手指按下的回调
     */
    override fun onDown(e: MotionEvent): Boolean {
        //如果移动还没有停止，强制停止
        if (!mScroller!!.isFinished) {
            mScroller!!.forceFinished(true)
        }
        return true
    }

    /**
     * @param e1        手指按下去的事件 开始的坐标
     * @param e2        当前手势事件
     * @param distanceX x方向移动的距离
     * @param distanceY y方向移动的距离
     */
    override fun onScroll(p0: MotionEvent?, e2: MotionEvent, distanceY: Float, p3: Float): Boolean {
        //上下移动的时候，需要改变显示的区域 改mRect
        mRect!!.offset(0, distanceY.toInt())
        //处理上下边界问题
        if (mRect!!.top < 0) {
            mRect!!.top = 0
            mRect!!.bottom = (mViewHeight / mScale).toInt()
        }
        if (mRect!!.bottom > mImageHeight) {
            mRect!!.top = mImageHeight - (mViewHeight / mScale).toInt()
            mRect!!.bottom = mImageHeight
        }
        invalidate()
        return false
    }

    /**
     * @param e1        手指按下去的事件 开始的坐标
     * @param e2        当前手势事件
     * @param velocityX 每秒移动的x像素点
     * @param velocityY 每秒移动的y像素点
     */
    override fun onFling(p0: MotionEvent?, e2: MotionEvent, velocityY: Float, p3: Float): Boolean {
        mScroller!!.fling(0, mRect!!.top,
            0, (-velocityY).toInt(),
            0, 0,
            0, mImageHeight - (mViewHeight / mScale).toInt())
        return false
    }


    override fun onShowPress(e: MotionEvent) {}
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {}

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller!!.isFinished) {
            return
        }
        //true 当前滑动还没有结束
        if (mScroller!!.computeScrollOffset()) {
            mRect!!.top = mScroller!!.currY
            mRect!!.bottom = mRect!!.top + (mViewHeight / mScale).toInt()
            invalidate()
        }
    }

}