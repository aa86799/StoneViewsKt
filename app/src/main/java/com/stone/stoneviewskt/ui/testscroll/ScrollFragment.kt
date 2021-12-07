package com.stone.stoneviewskt.ui.testscroll

import android.os.Bundle
import android.util.Log
import androidx.core.view.updateLayoutParams
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_scroll.*

/**
 * 滚动到底
 */
class ScrollFragment: BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val maxH = 600*3
        val minH = 600*2
        tv_second.setOnClickListener {
            tv_third.updateLayoutParams {
                if (maxH != this.height) {
                    this.height = maxH
                } else {
                    this.height = minH
                }
            }
            Log.i("TAG", "onPreparedView: ${tv_third.bottom}")
            sv_scroll.post {
                sv_scroll.smoothScrollTo(0, tv_third.bottom) // 滚动到 某子view 的bottom
//                sv_scroll.fullScroll(View.FOCUS_DOWN) // 滚动到最底部
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_scroll
    }
}