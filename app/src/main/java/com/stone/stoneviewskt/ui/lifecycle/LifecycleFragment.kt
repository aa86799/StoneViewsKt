package com.stone.stoneviewskt.ui.lifecycle

import android.os.Bundle
import com.stone.stoneviewskt.base.BaseFragment

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2021/1/13 22:31
 */
class LifecycleFragment: BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        lifecycle.addObserver(MyObserver())
    }

    override fun getLayoutRes(): Int {
        return android.R.layout.simple_list_item_1
    }
}