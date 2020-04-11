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
 * time:    2020/4/6 17:48
 */
class ObliqueProgressbarFragment : BaseFragment() {

    private lateinit var mViewer: ObliqueProgressbarView

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            var progress = 0
            val speed = 5
            val random = Random(speed)
            while (progress < 100) {
                progress += random.nextInt(speed)
                mViewer.setProgress(progress.toFloat() / 100)
                delay((random.nextInt(speed) * 100).toLong())
            }
        }
    }

    override fun getLayoutRes(): Int {
        return 0
    }

    override fun getLayoutView(): View? {
        mViewer = ObliqueProgressbarView(mActivity)
        return mViewer
    }
}