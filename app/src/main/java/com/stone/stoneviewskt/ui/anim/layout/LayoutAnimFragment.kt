package com.stone.stoneviewskt.ui.anim.layout

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.logi
import kotlinx.android.synthetic.main.fragment_layout_anim.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/1/11 12:04
 */
class LayoutAnimFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_layout_anim_btn0.setOnClickListener {
            val anim = ObjectAnimator.ofInt(fragment_layout_anim_btn0.top, fragment_layout_anim_root.top)
            val trans = LayoutTransition()
            trans.setDuration(1000)
            trans.setAnimator(LayoutTransition.CHANGE_APPEARING, anim)
            fragment_layout_anim_root.layoutTransition = trans
            fragment_layout_anim_group.visibility = View.VISIBLE
            anim.addUpdateListener {
                logi("$anim: ${it.animatedFraction}  ${it.animatedValue}")
                fragment_layout_anim_root.alpha = 0xff * it.animatedFraction
                fragment_layout_anim_root.top = it.animatedValue as Int
            }
            anim.interpolator = OvershootInterpolator()
            anim.start()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_layout_anim
    }
}