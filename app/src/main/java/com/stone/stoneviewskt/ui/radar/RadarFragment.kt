package com.stone.stoneviewskt.ui.radar

import android.view.View
import com.stone.stoneviewskt.base.BaseFragment

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 17:48
 */
class RadarFragment: BaseFragment() {

    override fun getLayoutRes(): Int {
        return 0
    }

    override fun getLayoutView(): View? {
        return RadarView(_mActivity)
    }
}