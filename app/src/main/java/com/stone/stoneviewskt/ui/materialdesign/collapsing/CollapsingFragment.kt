package com.stone.stoneviewskt.ui.materialdesign.collapsing

import android.graphics.Color
import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.showShort
import kotlinx.android.synthetic.main.fragment_collapsing.*

/**
 * desc:    CollapsingToolbarLayout 折叠/展开 Toolbar
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/9 15:30
 */
class CollapsingFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_collapsing_tool_bar.title = "Stone-CollapsingFragment"
        fragment_collapsing_tool_bar.logo = resources.getDrawable(R.mipmap.ic_launcher) //logo 会在导航图标之后
        fragment_collapsing_tool_bar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_green_24dp)

        //Toolbar 导航按钮才有点击事件；还有就是 menu item。
        fragment_collapsing_tool_bar.setNavigationOnClickListener {
            showShort("hei hei")
        }


        fragment_collapsing_ctl.setCollapsedTitleTextColor(Color.RED)
        fragment_collapsing_ctl.setExpandedTitleColor(Color.MAGENTA)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_collapsing
    }
}