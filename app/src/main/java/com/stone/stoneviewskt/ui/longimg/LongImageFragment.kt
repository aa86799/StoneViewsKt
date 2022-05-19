package com.stone.stoneviewskt.ui.longimg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentLongImageBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/11/30 10:34
 */
class LongImageFragment : BaseBindFragment<FragmentLongImageBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentLongImageBinding {
        return inflateBinding(inflater, container)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        requireContext().assets.open("big.png").use {
            mBind.fragmentLongImageIv.setImage(it)
        }

    }

}