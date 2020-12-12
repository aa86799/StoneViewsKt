package com.stone.stoneviewskt.ui.imagefilter

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_image_filter.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/4 11:04
 */
class ImageFilterFragment : BaseFragment() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_image_filter_bw.setOnClickListener {
            start(ImageBlackWhiteFilterFragment())
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_image_filter
    }
}