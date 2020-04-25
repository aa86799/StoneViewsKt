package com.stone.stoneviewskt.ui.clock

import android.view.View
import com.stone.stoneviewskt.base.BaseFragment

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/15 21:11
 */
class ClockFragment: BaseFragment() {
    private lateinit var mView: ClockView

    override fun getLayoutRes(): Int {
        return 0
    }

    override fun getLayoutView(): View? {
        mView = ClockView(context)
        return mView
    }

}