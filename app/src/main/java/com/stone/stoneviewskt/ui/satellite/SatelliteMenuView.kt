package com.stone.stoneviewskt.ui.satellite

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import com.stone.stoneviewskt.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 06/07/2017 23 22
 */
class SatelliteMenuView : ViewGroup, View.OnClickListener {

    enum class Position(val id: Int) {
        POS_LEFT_TOP(1), POS_RIGHT_TOP(2), POS_LEFT_BOTTOM(4), POS_RIGHT_BOTTOM(8);
    }

    companion object {
        private const val STATUS_OPEN = 0 //菜单的状态 打开
        private const val STATUS_CLOSE = 1 //菜单的状态 关闭
    }

    private var mStatus = STATUS_CLOSE

    private var mPosition: Position? = null
    private var mRadius: Float = 0f
    private var mMenuButton: View? = null
    private var mMenuItemClickListener: ((view: View, position: Int) -> Unit)? = null
    private var layoutChanged = false

    constructor(context: Context) : super(context) {
        initParams(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initParams(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initParams(context, attrs, defStyleAttr)
    }

    fun setOnMenuItemClickListener(listener: (View, Int) -> Unit) {
        this.mMenuItemClickListener = listener
    }

    fun setPosition(position: Position) {
        this.mPosition = position

        var child: View
        val count = childCount
        for (i in 0 until count) {
            child = getChildAt(i)
            child.clearAnimation()
        }

        layoutChanged = true
        mStatus = STATUS_CLOSE

//        invalidate() //会触发 测量、布局和绘制
        requestLayout() //这里只要请求布局
    }

    private fun initParams(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SatelliteMenu)
            val positionId = typedArray.getInt(R.styleable.SatelliteMenu_sm_position, Position.POS_LEFT_TOP.id)
            mPosition = matchPositionById(positionId)
            mRadius = typedArray.getDimension(R.styleable.SatelliteMenu_sm_radius, 100f)
            typedArray.recycle()
        } else {
            mPosition = Position.POS_LEFT_TOP
            mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)
        }
    }

    /** 根据位置的id值，匹配出对应位置的枚举对象 */
    private fun matchPositionById(id: Int): Position {
        var position: Position = Position.POS_LEFT_TOP
        Position.values().forEach {
            when (id) {
                it.id -> position = it
            }
        }
        return position
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量子view
        (0 until childCount).forEach {
            measureChild(getChildAt(it), widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (!changed && !layoutChanged) return

        layoutChanged = false

        layoutMenuButton()

        /*
        分析：
            menuButton距离每个item为radius。
            到item作直线，其夹角，应为90度均分。90/(item-1)=每个夹角的度数。
            有角度，就能求出正弦值sin(a)。
            根据正弦公式：sin(a)=a/c，且已知c=radius，求出a边长，即x坐标。
            有角度，就能求出余弦值cos(a)。
            余弦公式：cos(a)=b/c,且已知radius(斜边)，求出b边长，即y坐标

            对所有子菜单，弧形分布，在一个半径 mRadius 内
         */
        val count = childCount
        val angle = 90.0f / (count - 2) //这里-2，是减去了一个 menuButton(即加号图按钮)； 且因 6个菜单5个夹角，所以再减1
        var child: View
        (1 until count).forEach {
            child = getChildAt(it)
            child.visibility = View.GONE
            val w = child.measuredWidth
            val h = child.measuredHeight
            //Math.toRadians：math.pi/180 * angle = 弧度   angel/180*pi<==>angel*pi/180
            val radians = Math.toRadians(angle.toDouble() * (it - 1))//第i个角度的 sin(0)=0   i-1即从0开始,会有与屏幕直角边平行的 math.sin需要传弧度值
            val sin = sin(radians)
            val cos = cos(radians)
            var cl = (mRadius * sin).toInt() //原始Position=LEFT_TOP时的x坐标
            var ct = (mRadius * cos).toInt() //原始Position=LEFT_TOP时的y坐标

            //右上、右下 left值一样: 从右向左 递减
            if (mPosition == Position.POS_RIGHT_TOP || mPosition == Position.POS_RIGHT_BOTTOM) {
                cl = measuredWidth - w - cl
            }
            //左下、右下 top值一样: 从上向下 递增
            if (mPosition == Position.POS_LEFT_BOTTOM || mPosition == Position.POS_RIGHT_BOTTOM) {
                ct = measuredHeight - h - ct
            }

            child.layout(cl, ct, cl + w, ct + h)

            child.setOnClickListener { v ->
                mMenuItemClickListener?.invoke(v, it)
                itemAnim(it)
                mStatus = STATUS_CLOSE
            }
        }
    }

    /**
     * 菜单按钮设置 布局位置
     */
    private fun layoutMenuButton() {
        mMenuButton = getChildAt(0)

        //在屏幕四个角落 都有固定的坐标值
        var left = 0
        var top = 0
        val w = measuredWidth
        val h = measuredHeight
        val bw = mMenuButton!!.measuredWidth
        val bh = mMenuButton!!.measuredHeight
        when (mPosition) {
            Position.POS_LEFT_TOP -> {
                left = 0
                top = 0
            }
            Position.POS_LEFT_BOTTOM -> {
                left = 0
                top = h - bh
            }

            Position.POS_RIGHT_TOP -> {
                left = w - bw
                top = 0
            }

            Position.POS_RIGHT_BOTTOM -> {
                left = w - bw
                top = h - bh
            }

            else -> {

            }
        }

        mMenuButton?.layout(left, top, left + bw, top + bh)
        mMenuButton?.setOnClickListener(this)
    }

    /** MenuButton 点击 */
    override fun onClick(v: View?) {
        if (v == mMenuButton) {
            rotateMenuButton(mMenuButton!!, 360f, 500)
            toggleMenu(500)
        }
    }

    /** 旋转 MenuButton */
    private fun rotateMenuButton(view: View, angle: Float, duration: Long) {
        val anim = RotateAnimation(0f, angle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = duration
        anim.fillAfter = true //view保持在动画结束位置
        view.startAnimation(anim)
    }

    /**
     * 展开/隐藏子菜单
     * 子菜单动画 平移
     */
    private fun toggleMenu(duration: Long) {
        val count = childCount
        (1 until count).forEach {
            val child = getChildAt(it)
            /*
            平移动画，以相应弧度算出的x、y值，再乘以1或-1；
               close：
                   左上   r->l b->t
                   右上   l->r b->t
                   左下   r->l t->b
                   右下   l->r t->b
               open：
                   左上
                   右上
                   左下
                   右下

            */
            var xFlag = 1
            var yFlag = 1

            /*
             * 因，onLayout()中 将 子menu 的位置设置好了(弧形的)，
             * 后续使用 平移的 animation 时，会用到 相对于 子menuView自身的 x、y偏移值；
             * 对于 左上、左下： 子menu 到 plus 按钮位置，x为负数；即x要左移；所以 xFlag = -1
             * 对于 左上、右上： 子menu 到 plus 按钮位置，y为负数；即y要上移；所以 yFlag = -1
             *
             */
            if (mPosition == Position.POS_LEFT_TOP || mPosition == Position.POS_LEFT_BOTTOM) {
                xFlag = -1
            }

            if (mPosition == Position.POS_LEFT_TOP || mPosition == Position.POS_RIGHT_TOP) {
                yFlag = -1
            }

            // 一个圆的弧度是2π,角度是360°   π/2即90度的弧度
            val oppositeLen = (mRadius * sin(Math.PI / 2 / (count - 2) * (it - 1))).toInt() //对边 横向长
            val adjacentLen = (mRadius * cos(Math.PI / 2 / (count - 2) * (it - 1))).toInt() //邻边 纵向长
            val stopX: Float = (xFlag * oppositeLen).toFloat()
            val stopY: Float = (yFlag * adjacentLen).toFloat()

            val set = AnimationSet(true)
            if (mStatus == STATUS_OPEN) {//如是打开，则要关闭
                //4个值是起始点和结束点,相对于自身x、y的距离   自身原本的偏移值为0
                val tranAnim = TranslateAnimation(0f, stopX, 0f, stopY)
                set.addAnimation(tranAnim)
                val alphaAnim = AlphaAnimation(1.0f, 0f)
                set.addAnimation(alphaAnim)
                set.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        setItemClickable(child, false)
                    }

                    override fun onAnimationEnd(animation: Animation) {

                    }

                    override fun onAnimationRepeat(animation: Animation) {

                    }
                })

            } else { //如果是关闭，则要打开
                val tranAnim = TranslateAnimation(stopX, 0f, stopY, 0f)
                set.addAnimation(tranAnim)
                val alphaAnim = AlphaAnimation(0.0f, 1.0f)
                set.addAnimation(alphaAnim)
                set.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {
                        setItemClickable(child, false)
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        setItemClickable(child, true)
                    }

                    override fun onAnimationRepeat(animation: Animation) {

                    }
                })
            }

            set.duration = duration
            set.fillAfter = true
            child.startAnimation(set)
        }

        mStatus = if (mStatus == STATUS_OPEN) {
            STATUS_CLOSE
        } else {
            STATUS_OPEN
        }
    }

    /**
     * item点击动画
     * @param position
     */
    private fun itemAnim(position: Int) {
        var child: View
        val count = childCount
        for (i in 1 until count) {
            child = getChildAt(i)
            if (position == i) {
                scaleBigAnim(child)
            } else {
                scaleSmallAnim(child)
            }
            setItemClickable(child, false)
        }
    }

    /**
     * 放大动画
     */
    private fun scaleBigAnim(view: View) {
        val scaleAnim = ScaleAnimation(
                1.0f, 3f, 1.0f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        val alphaAnim = AlphaAnimation(1.0f, 0f)
        val set = AnimationSet(true)
        set.addAnimation(alphaAnim)
        set.addAnimation(scaleAnim)
        set.duration = 800
        set.fillAfter = true
        view.startAnimation(set)
    }

    /**
     * 缩小动画
     */
    private fun scaleSmallAnim(view: View) {
        val scaleAnim = ScaleAnimation(
                1.0f, 0f, 1.0f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        val alphaAnim = AlphaAnimation(1.0f, 0f)
        val set = AnimationSet(true)
        set.addAnimation(alphaAnim)
        set.addAnimation(scaleAnim)
        set.fillAfter = true
        set.duration = 500
        view.startAnimation(set)
    }

    private fun setItemClickable(view: View, flag: Boolean) {
        view.isClickable = flag
        view.isFocusable = flag
    }
}
