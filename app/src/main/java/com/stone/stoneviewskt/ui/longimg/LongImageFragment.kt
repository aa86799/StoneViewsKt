package com.stone.stoneviewskt.ui.longimg

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_long_image.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/11/30 10:34
 */
class LongImageFragment: BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        requireContext().assets.open("big.png").use {
            fragment_long_image_iv.setImage(it)
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_long_image
    }
}