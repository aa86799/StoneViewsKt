package com.stone.stoneviewskt.recyclerview

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentRvDemoBinding
import com.stone.stoneviewskt.recyclerview.multiviewype.MultiViewTypeFragment

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/24
 */
class RecyclerViewDemoFragment : BaseBindFragment<FragmentRvDemoBinding>(R.layout.fragment_rv_demo) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.btnMultiViewType.setOnClickListener {
            start(MultiViewTypeFragment())
        }
    }
}