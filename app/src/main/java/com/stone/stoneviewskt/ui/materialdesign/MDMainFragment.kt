package com.stone.stoneviewskt.ui.materialdesign

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.adapter.SampleAdapter
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.ui.materialdesign.tabv.TabLayoutFragment
import kotlinx.android.synthetic.main.fragment_md_main.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/25 12:53
 */
class MDMainFragment: BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_md_main_title.mIvLeft.setOnClickListener { mActivity.finish() }

        fragment_md_main_rv.adapter = SampleAdapter(TITLES) { _, title ->
            when (title) {
                "TabLayout+ViewPager" -> (mActivity as BaseActivity).startNewUI(TabLayoutFragment::class.java)
//                "CoordinatorLayout+AppBarLayout+TabLayout+NestedScrollView" ->
            }
        }

    }

    companion object {
        val TITLES = listOf(
            "TabLayout+ViewPager",
            "CoordinatorLayout+AppBarLayout+TabLayout+NestedScrollView"
        )
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_md_main
    }
}