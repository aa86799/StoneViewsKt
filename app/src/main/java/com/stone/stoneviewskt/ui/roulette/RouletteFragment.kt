package com.stone.stoneviewskt.ui.roulette

import android.os.Bundle
import android.view.View
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentRouletteBinding
import com.stone.stoneviewskt.util.showShort

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 11:49
 */
class RouletteFragment : BaseBindFragment<FragmentRouletteBinding>(R.layout.fragment_roulette) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBind.activityRouletteRv.setOnPartClickListener {
            when (it) {
                -1 -> {
                    showShort("未点击扇形区")
                }
                else -> {
                    showShort("扇形区索引=$it")
                }
            }
        }

        mBind.activityRouletteRv.startRotate()
    }

//    override fun getLayoutRes(): Int {
//        return R.layout.fragment_roulette
//    }

//    override fun getLayoutView(): View? {
//        val v = RouletteView(context!!).apply {
//            mRadius = 300
//            mPart = 8
//        }
//        return v
//    }
}