package com.stone.stoneviewskt.ui.fab

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_floatingaction_button.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/17 19:12
 */
class FloatingActionButtonFragment : BaseFragment() {

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
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_floatingaction_button
    }
}