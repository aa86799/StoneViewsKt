package com.stone.stoneviewskt.ui.floatview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.updateLayoutParams
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
class GodFloatView : FrameLayout {

    private var mDownX: Float = 0f
    private var mDownY: Float = 0f
    private var mDecorView: View? = null
    private var mOnClickListener: OnClickListener? = null
    private var mClickDownTime = 0L
    private val mTouchSlop = android.view.ViewConfiguration.get(context).scaledTouchSlop
    private var mIsMoveAction = false

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

        this.setOnClickListener {
            mOnClickListener?.onClick(it)
        }
    }

    fun addToWindow(window: Window) {
        mDecorView = window.decorView as FrameLayout
        mDecorView?.post {
            mDecorView?.findViewById<FrameLayout>(android.R.id.content)?.addView(this)
            mDecorView?.postDelayed({
                val xDistance = -measuredWidth / 2
                val yDistance = getScreenHeight() - measuredHeight / 3 * 2
                updateLayoutParams<FrameLayout.LayoutParams> {
                    this.leftMargin = xDistance
                    this.topMargin = yDistance
                }
            }, 500)
        }
    }

   override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = event.x
                mDownY = event.y
                mClickDownTime = System.currentTimeMillis()
                mIsMoveAction = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs(y - mDownY) > mTouchSlop || abs(x - mDownX) > mTouchSlop) {
                    mIsMoveAction = true
                }
                offsetTopAndBottom((y - mDownY).toInt())
                offsetLeftAndRight((x - mDownX).toInt())
            }
            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis() - mClickDownTime < 600 && !mIsMoveAction) {
                    mOnClickListener?.onClick(this@GodFloatView)
                }
                adsorbTopAndBottom(event)
            }
        }
        return true
    }

    private fun adsorbTopAndBottom(event: MotionEvent) {
        val centerY = measuredHeight / 2 + top
        val yDistance = if (centerY < getScreenHeight() / 2) {
            0 + 44.dp - measuredHeight / 2 // 状态栏高度44dp； 吸顶
        } else {
            getScreenHeight() - measuredHeight / 3 * 2 // 吸底
        }
        animate().setInterpolator(DecelerateInterpolator()).setDuration(300).y(yDistance.toFloat()).start()

        val centerX = measuredWidth / 2 + left
        val xDistance = if (centerX < getScreenWidth() / 2) {
            -measuredWidth / 2
        } else {
            getScreenWidth() - measuredWidth / 2
        }
        animate().setInterpolator(DecelerateInterpolator()).setDuration(300).x(xDistance.toFloat()).start()
    }

    private fun getScreenHeight() = context.resources.displayMetrics.heightPixels

    private fun getScreenWidth() = context.resources.displayMetrics.widthPixels

    fun setClick(onClickListener: OnClickListener) {
        this.mOnClickListener = onClickListener
    }

}