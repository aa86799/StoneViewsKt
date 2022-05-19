package com.stone.stoneviewskt.ui.testscroll

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentScrollBinding

/**
 * 滚动到底
 */
class ScrollFragment : BaseBindFragment<FragmentScrollBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentScrollBinding {
        return inflateBinding(inflater, container)
    }

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