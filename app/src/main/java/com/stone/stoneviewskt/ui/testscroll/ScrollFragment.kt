package com.stone.stoneviewskt.ui.testscroll

import android.os.Bundle
import android.util.Log
import androidx.core.view.updateLayoutParams
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentScrollBinding

/**
 * desc:    滚动到底
 * author:  stone
 * email:   aa86799@163.com
 * time:    2021/12/7
 */
class ScrollFragment : BaseBindFragment<FragmentScrollBinding>(R.layout.fragment_scroll) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val maxH = 600 * 3
        val minH = 600 * 2
        mBind.tvSecond.setOnClickListener {
            mBind.tvThird.updateLayoutParams {
                if (maxH != this.height) {
                    this.height = maxH
                } else {
                    this.height = minH
                }
            }
            Log.i("TAG", "onPreparedView: ${mBind.tvThird.bottom}")
            mBind.svScroll.post {
                mBind.svScroll.smoothScrollTo(0, mBind.tvThird.bottom) // 滚动到 某子view 的bottom
//                sv_scroll.fullScroll(View.FOCUS_DOWN) // 滚动到最底部
            }
        }
    }

}