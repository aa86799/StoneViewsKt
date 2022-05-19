package com.stone.stoneviewskt.ui.materialdesign.tabv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentVpBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/25 19:18
 */
class VpFragment : BaseBindFragment<FragmentVpBinding>() {

    var mData: String = ""

    companion object {
        const val KEY_DATA = "key_data"
    }

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentVpBinding {
        return inflateBinding(inflater, container)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mData = arguments?.getString(KEY_DATA) ?: return
        mBind.fragmentVpTv.text = mData
    }
}