package com.stone.stoneviewskt.ui.contentp

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentCphBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/12/11 15:44
 */
class ContentProviderHomeFragment : BaseBindFragment<FragmentCphBinding>(R.layout.fragment_cph) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mBind.root
    }
}