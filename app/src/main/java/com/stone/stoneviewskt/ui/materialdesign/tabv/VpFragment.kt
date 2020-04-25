package com.stone.stoneviewskt.ui.materialdesign.tabv

import android.os.Bundle
import android.widget.TextView
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vp.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/25 19:18
 */
class VpFragment: BaseFragment() {

    var mData: String = ""

    companion object {
        const val KEY_DATA = "key_data"
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mData = arguments?.getString(KEY_DATA) ?: return
        fragment_vp_tv.text = mData
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_vp
    }
}