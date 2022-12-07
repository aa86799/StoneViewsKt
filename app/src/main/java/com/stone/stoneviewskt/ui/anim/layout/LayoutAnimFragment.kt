package com.stone.stoneviewskt.ui.anim.layout

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentLayoutAnim2Binding
import com.stone.stoneviewskt.util.logi

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/1/11 12:04
 */
// FragmentLayoutAnim1Binding
class LayoutAnimFragment : BaseBindFragment<FragmentLayoutAnim2Binding>(R.layout.fragment_layout_anim2) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        //属性动画
        mBind.fragmentLayoutAnimBtn0.setOnClickListener {
            val anim = ObjectAnimator.ofInt(mBind.fragmentLayoutAnimBtn0.top, mBind.fragmentLayoutAnimRoot.top)
            val trans = LayoutTransition()
            trans.setDuration(2000)
            trans.setAnimator(LayoutTransition.CHANGE_APPEARING, anim)
            mBind.fragmentLayoutAnimRoot.layoutTransition = trans
            mBind.fragmentLayoutAnimGroup.visibility = View.VISIBLE
            anim.addUpdateListener {
                logi("$anim: ${it.animatedFraction}  ${it.animatedValue}")
                mBind.fragmentLayoutAnimRoot.alpha = 0xff * it.animatedFraction
                mBind.fragmentLayoutAnimIv.top = it.animatedValue as Int
                mBind.fragmentLayoutAnimGroup.top = it.animatedValue as Int
            }
            anim.interpolator = OvershootInterpolator()
            anim.start()
        }
    }


}