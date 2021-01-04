package com.stone.stoneviewskt.ui.fab

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_floatingaction_button.fragment_floatingaction_button_bottom
import kotlinx.android.synthetic.main.fragment_floatingaction_button.fragment_floatingaction_button_btn
import kotlinx.android.synthetic.main.fragment_floatingaction_button.fragment_floatingaction_button_top
import kotlinx.android.synthetic.main.fragment_floatingaction_button_begin.*

/**
 * desc:    悬浮按钮会处于它所在层次中的前景foreground.
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/17 19:12
 */
class FloatingActionButtonFragment : BaseFragment() {

    private var mIsShowMenu = false

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        var flag = true
        fragment_floatingaction_button_btn.setOnClickListener {
            flag = !flag
            //show animation使小部件变大并淡入，而hide animation使小部件变小并淡入。
            if (flag) fragment_floatingaction_button_bottom.show()
            else fragment_floatingaction_button_bottom.hide()
        }

        fragment_floatingaction_button_top.setOnClickListener {
            //展开和收缩
            flag = !flag
            if (flag) fragment_floatingaction_button_top.extend()
            else fragment_floatingaction_button_top.shrink()
        }

        /*
         * 约束动画
         * 要求 约束布局下的每个子view都有 id， 否则报， RuntimeException: All children of ConstraintLayout must have ids to use ConstraintSet.
         *
         */
        val set1 = ConstraintSet()
        val set2 = ConstraintSet()
        val root: ConstraintLayout = fragment_fa_button_begin
        set1.clone(root)
        set2.clone(requireContext(), R.layout.fragment_floatingaction_button)
        fragment_floatingaction_button_bottom.setOnClickListener {
            TransitionManager.beginDelayedTransition(root)
            mIsShowMenu = !mIsShowMenu
            if (mIsShowMenu) {
                set2.applyTo(root)
            } else {
                set1.applyTo(root)
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_floatingaction_button_begin
    }
}