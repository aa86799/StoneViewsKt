package com.stone.stoneviewskt.ui.materialdesign.tabcoor

import android.graphics.Color
import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_appbar_layout.*

/**
 * desc:    CoordinatorLayout+AppBarLayout+NestedScrollView 滚动效果
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/27 11:06
 */
class TabCoorAppbarFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        /*
         * Toolbar
         * 设置标题、子标题，文本与颜色；
         * logo, collapseIcon 收缩图片，navigationIcon 导航图片
         */
        fragment_appbar_layout_tb.title = "Title"
        fragment_appbar_layout_tb.setTitleTextColor(Color.WHITE)
        fragment_appbar_layout_tb.subtitle = "subTitle"
        fragment_appbar_layout_tb.setSubtitleTextColor(Color.YELLOW)
        fragment_appbar_layout_tb.collapseIcon = resources.getDrawable(R.drawable.satellite_icn_plus)
        fragment_appbar_layout_tb.navigationIcon = resources.getDrawable(R.mipmap.satellite_music)
        fragment_appbar_layout_tb.overflowIcon = resources.getDrawable(R.mipmap.satellite_thought)
        fragment_appbar_layout_tb.logo = resources.getDrawable(R.drawable.satellite_button)

        for (tab in TABS) {
            fragment_appbar_layout_tl.addTab(fragment_appbar_layout_tl.newTab().setText(tab))
        }

        val cc =  fun():String {
            val result = StringBuilder()
            (0..1000).forEach {
                result.append(it).append("_")
            }
            return result.toString()
        }

        fragment_appbar_layout_tv.text = cc()
    }

    companion object {
        private val TABS = (1..10).map { "$it" }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_appbar_layout
    }
}