package com.stone.stoneviewskt.ui.floatview

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.util.dp
import kotlin.math.abs

/**
 * desc:    悬浮 View。
 *          支持上、下、左、右四角吸附。支持单击、拖动
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/1/30 08:51
 */
class GodFloatViewWithWindowManager : FrameLayout {

    private var mLastX: Float = 0f
    private var mLastY: Float = 0f
    private var mDecorView: View? = null
    private var mOnClickListener: OnClickListener? = null
    private var mClickDownTime = 0L
    private val mTouchSlop = android.view.ViewConfiguration.get(context).scaledTouchSlop
    private var mIsMoveAction = false
    private lateinit var mWindowManager: WindowManager
    private lateinit var mLp: WindowManager.LayoutParams

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        initView()
    }

    private fun initView() {
        val lp = LayoutParams(100.dp, 100.dp)
        layoutParams = lp

        val cardView = CardView(context)
        cardView.radius = 50.dp.toFloat()
        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.ic_launcher)
        cardView.addView(imageView)
        addView(cardView)

        setBackgroundColor(Color.TRANSPARENT)

        this.setOnClickListener {
            mOnClickListener?.onClick(it)
        }
    }

    fun addToWindow(window: Window) {
        mWindowManager = window.windowManager
        mDecorView = window.decorView as FrameLayout
        mDecorView?.post {
            mLp = WindowManager.LayoutParams()
            mLp.width = WindowManager.LayoutParams.WRAP_CONTENT
            mLp.height = WindowManager.LayoutParams.WRAP_CONTENT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mLp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                mLp.type = WindowManager.LayoutParams.TYPE_PHONE
            }
            //设置可以显示在状态栏上
            mLp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            mLp.format = PixelFormat.RGBA_8888
            mLp.alpha = 0.5f // 透明
            mWindowManager.addView(this, mLp)

//            mDecorView?.postDelayed({
//                // 默认是居中的   如下设置，效果是  左上角
//                mLp.x = -getScreenWidth() / 2
//                mLp.y = -getScreenHeight() / 2
//                mWindowManager.updateViewLayout(this, mLp)
//            }, 500)

        }
    }

   override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.rawX
        val y = event.rawY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = x
                mLastY = y
                mClickDownTime = System.currentTimeMillis()
                mIsMoveAction = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs(y - mLastY) > mTouchSlop || abs(x - mLastX) > mTouchSlop) {
                    mIsMoveAction = true
                }
                mLp.x += (x - mLastX).toInt()
                mLp.y += (y - mLastY).toInt()
                mWindowManager.updateViewLayout(this@GodFloatViewWithWindowManager, mLp)
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis() - mClickDownTime < 600 && !mIsMoveAction) {
                    mOnClickListener?.onClick(this@GodFloatViewWithWindowManager)
                }
                mWindowManager.updateViewLayout(this, mLp)
//                adsorbTopAndBottom(event)
            }
        }
        return true
    }

    // todo 有问题
    // 默认添加后，在中间时，拖动松开后，不使用如下方法，是正常的，拖动到哪里停在哪里
    // 但当设置停留在左上角，并使用如下后，首次吸在 上下左右某角时，是正常的；但再次拖动 就不正常了
    private fun adsorbTopAndBottom(event: MotionEvent) {
        val centerY = measuredHeight / 2 + mLp.y
        mLp.y = if (centerY < 0) { // 中间是 0
            -getScreenHeight() / 2
        } else {
            getScreenHeight()
        }

        val centerX = measuredWidth / 2 + mLp.x
        mLp.x = if (centerX < 0) { // 中间是 0
            -getScreenWidth() / 2
        } else {
            getScreenWidth()
        }
        mWindowManager.updateViewLayout(this, mLp)
    }

    private fun getScreenHeight() = context.resources.displayMetrics.heightPixels

    private fun getScreenWidth() = context.resources.displayMetrics.widthPixels

    fun setClick(onClickListener: OnClickListener) {
        this.mOnClickListener = onClickListener
    }

}