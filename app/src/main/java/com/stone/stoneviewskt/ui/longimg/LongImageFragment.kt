package com.stone.stoneviewskt.ui.longimg

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentLongImageBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/11/30 10:34
 */
class LongImageFragment : BaseBindFragment<FragmentLongImageBinding>(R.layout.fragment_long_image) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

//        requireContext().assets.open("big.png").use {
        requireContext().assets.open("qinlan.jpg").use {
            mBind.fragmentLongImageIv.setImage(it)
        }

    }

}