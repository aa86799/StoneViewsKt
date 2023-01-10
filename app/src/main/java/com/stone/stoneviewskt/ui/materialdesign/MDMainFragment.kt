package com.stone.stoneviewskt.ui.materialdesign

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.adapter.SampleAdapter2
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentMdMainBinding
import com.stone.stoneviewskt.ui.card.CardFragment
import com.stone.stoneviewskt.ui.fab.FloatingActionButtonFragment
import com.stone.stoneviewskt.ui.materialdesign.bab.BabFragment
import com.stone.stoneviewskt.ui.materialdesign.bnv.BnvFragment
import com.stone.stoneviewskt.ui.materialdesign.bsb.BsbDialogOnlyFragment
import com.stone.stoneviewskt.ui.materialdesign.bsb.BsbFragment
import com.stone.stoneviewskt.ui.materialdesign.chips.ChipsFragment
import com.stone.stoneviewskt.ui.materialdesign.collapsing.CollapsingFragment
import com.stone.stoneviewskt.ui.materialdesign.tabcoor.TabCoorAppbarFragment
import com.stone.stoneviewskt.ui.materialdesign.tabv.TabLayoutFragment

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/25 12:53
 */
class MDMainFragment : BaseBindFragment<FragmentMdMainBinding>(R.layout.fragment_md_main) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.fragmentMdMainTitle.mIvLeft.setOnClickListener { _mActivity.finish() }

        mBind.fragmentMdMainRv.adapter = SampleAdapter2(TITLES) { _, title ->
            when (title) {
                "TabLayout+ViewPager" -> (_mActivity as BaseActivity).startNewUI(TabLayoutFragment::class.java)
                "CoordinatorLayout+AppBarLayout+TabLayout+NestedScrollView" -> (_mActivity as BaseActivity).startNewUI(TabCoorAppbarFragment::class.java)
                "BottomNavigationView" -> (_mActivity as BaseActivity).startNewUI(BnvFragment::class.java)
                "BottomAppBar" -> (_mActivity as BaseActivity).startNewUI(BabFragment::class.java)
                "BottomSheetBehavior" -> (_mActivity as BaseActivity).startNewUI(BsbFragment::class.java)
                "BsbDialogOnlyFragment" -> (_mActivity as BaseActivity).startNewUI(BsbDialogOnlyFragment::class.java)
                "Chips" -> (_mActivity as BaseActivity).startNewUI(ChipsFragment::class.java)
                "Card" -> (_mActivity as BaseActivity).startNewUI(CardFragment::class.java)
                "FloatingActionButton" -> (_mActivity as BaseActivity).startNewUI(FloatingActionButtonFragment::class.java)
                "CollapsingToolbarLayout" -> (_mActivity as BaseActivity).startNewUI(CollapsingFragment::class.java)
            }
        }

    }

    companion object {
        val TITLES = listOf(
            "TabLayout+ViewPager",
            "CoordinatorLayout+AppBarLayout+TabLayout+NestedScrollView",
            "BottomNavigationView",
            "BottomAppBar",
            "BsbDialogOnlyFragment",
            "Chips",
            "Card",
            "FloatingActionButton",
            "CollapsingToolbarLayout"
        )
    }

}