package com.stone.stoneviewskt.ui.progressbar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/11 17:38
 */
class CircleProgressbarFragment: BaseFragment() {

    private lateinit var mBar: CircleProgressbarView

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            var progress = 0f
            val speed = 1
            val random = Random(speed)
            mBar.setProgress(0.01f)
            while (progress < 100) {
                delay((random.nextInt(speed * 2) * 50).toLong())
                progress += random.nextFloat()
                mBar.setProgress(progress)
            }
        }
    }

    override fun getLayoutRes(): Int {
        return 0
    }

    override fun getLayoutView(): View? {
        mBar = CircleProgressbarView(mActivity)
        return mBar
    }
}