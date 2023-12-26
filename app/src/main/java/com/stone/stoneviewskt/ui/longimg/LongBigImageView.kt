package com.stone.stoneviewskt.ui.longimg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import com.stone.stoneviewskt.util.logi
import java.io.IOException
import java.io.InputStream

class LongBigImageView : View, View.OnTouchListener, GestureDetector.OnGestureListener {

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        initViews()
    }

    constructor(context: Context) : this(context, null, 0) {
        initViews()
    }

    private lateinit var mRect: Rect
    private lateinit var mOptions: BitmapFactory.Options
    private lateinit var mGestureDetector: GestureDetector
    private lateinit var mScroller: Scroller
    private var mImageWidth = 0
    private var mImageHeight = 0
    private var mDecoder: BitmapRegionDecoder? = null
    private var mViewWidth = 0
    private var mViewHeight = 0
    private var mBitmap: Bitmap? = null
    private var mScaleX = 0f
    private var mScaleY = 0f
    private val matrix = Matrix()

    private fun initViews() {
        //第一步 设置BigView需要的成员变量
        //设置一个矩形区域(矩形区域框定)
        mRect = Rect()
        //用于内存复用(设置编码格式)
        mOptions = BitmapFactory.Options()
        //手势支持
        mGestureDetector = GestureDetector(context, this)
        //滚动类
        mScroller = Scroller(context)
        //触摸时触发事件
        setOnTouchListener(this)
    }

    //第二步设置图片
    fun setImage(stream: InputStream?) {
        //获取图片的宽和高
        //此时不能将整张图片加载进来，这样内存复用无意义，需要使用inJustDecodeBounds方法，只加载部分区域来获取图片宽高
        mOptions.inJustDecodeBounds = true
        //将is传进去解码就能获取到图片的宽和高
        BitmapFactory.decodeStream(stream, null, mOptions)
        mImageWidth = mOptions.outWidth
        mImageHeight = mOptions.outHeight
        //  logi("mImageWidth: --->"+mImageWidth);
        //  logi("mImageHeight: --->"+mImageHeight);
        //开启内存复用
        mOptions.inMutable = true

        //设置图片格式:rgb565
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565

        //用完需要关闭
        mOptions.inJustDecodeBounds = false

        //区域解码器
        try {
            mDecoder = BitmapRegionDecoder.newInstance(stream!!, false)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        requestLayout()
    }

    //第三步 加载图片
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        logi("执行onMeasure: ")
        mViewWidth = measuredWidth
        mViewHeight = measuredHeight
        logi("mViewWidth: --->$mViewWidth")
        logi("mViewHeight:---> $mViewHeight")
        //绑定图片加载区域
        //上边界为0
        mRect.top = 0
        //左边界为0
        mRect.left = 0
        //右边界为图片的宽度
        mRect.right = mImageWidth
        //下边界为view的高度
        mRect.bottom = mViewHeight
    }

    //第四步 画图
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        logi("执行onDraw: ")
        if (mDecoder == null) {
            //   logi("null: ---->");
            return
        }
        //内存复用
        //复用inBitmap这块的内存(每次滚动重新绘制都会复用这块内存，达到内存复用)
        mOptions.inBitmap = mBitmap
        //logi("mOptions.inBitmap--->"+mOptions.inBitmap);
        mBitmap = mDecoder!!.decodeRegion(mRect, mOptions)
        //logi("mBitmap--->"+mBitmap);
        //计算缩放因子
        mScaleX = mViewWidth / mImageWidth.toFloat()
        //  logi("mScaleX: "+mScaleX);
        mScaleY = mViewHeight / mImageHeight.toFloat()
        //  logi("mScaleY: "+mScaleY);
        //得到矩阵缩放
        matrix.reset()
        matrix.setScale(mScaleX, mScaleX) //如果matrix.setScale(mScaleX, mScaleY)则图片会在充满在当前的view的x和y轴
        canvas.drawBitmap(mBitmap!!, matrix, null)
    }

    //第五步 处理点击事件
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        logi("执行onTouch: ")
        //将Touch事件传递给手势
        return mGestureDetector.onTouchEvent(event)
    }

    //第六步 处理手势按下事件
    override fun onDown(e: MotionEvent): Boolean {
        logi("执行onDown: ")
        //如果滑动没有停止就 强制停止
        if (!mScroller.isFinished) {
            mScroller.forceFinished(true)
        }
        //将事件进行传递，接收后续事件
        //因为在GestureDetector中，onDown方法是用于监听手指按下事件的，如果不返回true消费该事件，
        // GestureDetector就不会将后续的事件传递给其他的方法进行处理，
        // 包括滑动事件。因此，如果要实现按下手指后进行滑动图片的效果，需要在onDown方法中返回true进行消费。
        return true
    }

    //第七步 处理滑动事件(手势)指手势的拖动
    //e1 开始事件
    //e2 即时事件也就是滑动时
    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        logi("执行onScroll: ")

        //上下滑动时，直接改变Rect的显示区域
        mRect.offset(0, distanceY.toInt()) //上下滑动只需要改变Y轴
        logi("onScroll----->distanceY--->: $distanceY")
        logi("onScroll----->mRect.bottom--->: " + mRect.bottom)
        //判断到顶和到底的情况
        if (mRect.bottom > mImageHeight) { //滑到最底
            logi("onScroll: 经来了")
            mRect.bottom = mImageHeight
            mRect.top = mImageHeight - mViewHeight
        }
        //  logi("mRect.top: "+mRect.top);
        if (mRect.top < 0) { //滑到最顶
            mRect.top = 0
            mRect.bottom = mViewHeight
        }
        invalidate()
        return true
    }

    //第八步 处理惯性问题(手势)指手势的滑动
    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        logi("执行onFling: ")
        //velocityY表示Y轴的惯性值，startX和startY为滑动的开始位置,minY和maxY为滑动距离的最小值和最大值
        //  logi("onFling: mRect.top -->"+mRect.top);
        mScroller.fling(0, mRect.top, 0, -velocityY.toInt(), 0, 0, 0, mImageHeight - mViewHeight)
        //   logi("velocityY: "+velocityY);
        //   logi("mRect.top");
        return false
    }

    //该方法可以获取当前的滚动值
    override fun computeScroll() {
        super.computeScroll()
        logi("执行computeScroll: ")
        //如果没有滚动，直接返回即可
        if (mScroller.isFinished) {
            return
        }
        //如果已经滚动到新位置返回true
        if (mScroller.computeScrollOffset()) {
            mRect.top = mScroller.currY
            logi("computeScroll:mRect.top " + mRect.top)
            mRect.bottom = mRect.top + mViewHeight //底部边框等于更新的top位置加上
            logi("computeScroll:mRect.bottom " + mRect.bottom)
        }
        invalidate()
    }

    override fun onShowPress(e: MotionEvent) {}
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {}

}