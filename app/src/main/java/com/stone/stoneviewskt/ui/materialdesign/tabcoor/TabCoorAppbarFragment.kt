package com.stone.stoneviewskt.ui.materialdesign.tabcoor

import android.graphics.Color
import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentAppbarLayoutBinding

/**
 * desc:    CoordinatorLayout+AppBarLayout+NestedScrollView 滚动效果
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/27 11:06
 */
class TabCoorAppbarFragment : BaseBindFragment<FragmentAppbarLayoutBinding>(R.layout.fragment_appbar_layout) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        /*
         * Toolbar
         * 设置标题、子标题，文本与颜色；
         * logo, collapseIcon 收缩图片，navigationIcon 导航图片
         */
        mBind.fragmentAppbarLayoutTb.title = "Title"
        mBind.fragmentAppbarLayoutTb.setTitleTextColor(Color.WHITE)
        mBind.fragmentAppbarLayoutTb.subtitle = "subTitle"
        mBind.fragmentAppbarLayoutTb.setSubtitleTextColor(Color.YELLOW)
        mBind.fragmentAppbarLayoutTb.collapseIcon = resources.getDrawable(R.drawable.satellite_icn_plus)
        mBind.fragmentAppbarLayoutTb.navigationIcon = resources.getDrawable(R.mipmap.satellite_music)
        mBind.fragmentAppbarLayoutTb.overflowIcon = resources.getDrawable(R.mipmap.satellite_thought)
        mBind.fragmentAppbarLayoutTb.logo = resources.getDrawable(R.drawable.satellite_button)

        for (tab in TABS) {
            mBind.fragmentAppbarLayoutTl.addTab(mBind.fragmentAppbarLayoutTl.newTab().setText(tab))
        }

        val cc = fun(): String {
            val result = StringBuilder()
            (0..1000).forEach {
                result.append(it).append("_")
            }
            return result.toString()
        }

        mBind.fragmentAppbarLayoutTv.text = cc()
    }

    companion object {
        private val TABS = (1..10).map { "$it" }
    }

}