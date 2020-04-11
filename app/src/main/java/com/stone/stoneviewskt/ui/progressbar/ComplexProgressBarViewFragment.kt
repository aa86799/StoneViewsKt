package com.stone.stoneviewskt.ui.progressbar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.coroutines.delay

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/11 18:28
 */
class ComplexProgressBarViewFragment: BaseFragment() {

    private lateinit var mBar: ComplexProgressBarView

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mBar.mHorizontalBarHeight = (Math.random() * 100 + 50).toFloat()
        mBar.mOvalRadius = (Math.random() * 100 + 10).toFloat()
        mBar.mProgress = 100

        mBar.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                repeat(100) {
                    mBar.mHorizontalBarHeight = (Math.random() * 400 + 100).toFloat()
                    mBar.mOvalRadius = (Math.random() * 100 + 10).toFloat()
                    mBar.mProgress = (Math.random() * 100 + 1).toInt()
                    delay(300)
                    if (it % 10 == 0) {
                        mBar.mProgress = 100
                        delay(800)
                    }
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return 0
    }

    override fun getLayoutView(): View? {
        mBar = ComplexProgressBarView(mActivity)
        return mBar
    }
}