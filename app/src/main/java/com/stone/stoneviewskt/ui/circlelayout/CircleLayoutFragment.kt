package com.stone.stoneviewskt.ui.circlelayout

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.stone.stoneviewskt.BuildConfig
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentCircleLayoutBinding
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/26 23:02
 */
class CircleLayoutFragment : BaseBindFragment<FragmentCircleLayoutBinding>(R.layout.fragment_circle_layout) {

    private var mIsShow = true

    override fun onPreparedView(savedInstanceState: Bundle?) {
        mBind.fragmentClMenu.show()
//        fragment_cl_menu.hide()

        mBind.fragmentClIp.onClick {
            alert {
                title = "当前服务地址"
                message = "http://192.168.1.11"
                yesButton {
                    it.dismiss()
                }
            }.show()
        }

        mBind.fragmentClTool.setOnClickListener {
            alert {
                title = "package name"
                message = BuildConfig.APPLICATION_ID
                yesButton {
                    it.dismiss()
                }
            }.show()
        }

        mBind.fragmentClMenu.setOnClickListener {
            it as ExtendedFloatingActionButton
            if (!mIsShow) {
                it.extend() //展开
//                it.show() //显示
            } else {
                it.shrink() //收缩
//                it.hide() //隐藏
            }
            switchMenu(!mIsShow)
        }

        mBind.fragmentClMenu.post {
            mBind.fragmentClMenu.performClick()
        }
    }


    //开关菜单
    private fun switchMenu(isShow: Boolean) {
        mIsShow = isShow
        val itemViews = mBind.fragmentClRoot.children.filterNot { it == mBind.fragmentClMenu }
        val radius = (resources.getDimension(R.dimen.dp_10) * 15).toInt()
        val start = if (isShow) 0 else radius
        val end = if (isShow) radius else 0
        val anim = ValueAnimator.ofInt(start, end)
        anim.duration = 500
        anim.addUpdateListener {
            val r = it.animatedValue as Int
            itemViews.forEach {
                val lp = it.layoutParams as ConstraintLayout.LayoutParams
                lp.circleRadius = r
                it.layoutParams = lp
            }
        }
//        anim.start()

        //两个动画一起 启动
        val animators = mutableListOf<Animator>(anim)
        itemViews.forEachIndexed { index, view ->
            val count = itemViews.count()
            animators.add(
                ObjectAnimator.ofFloat(
                    view,
                    "alpha",
                    if (isShow) 0f else 1f,
                    if (isShow) 1f else 0f
                ).apply {
//                    interpolator = BounceInterpolator() //弹跳式，闪闪的
//                    interpolator = OvershootInterpolator() //超越; 快速加速到顶
                    interpolator = LinearOutSlowInInterpolator() //先匀速 再缓慢减速
                }
            )
        }
        val set = AnimatorSet()
//        set.playSequentially(animators) //顺序执行
        set.playTogether(animators)  //同步执行
        set.start()
    }
}