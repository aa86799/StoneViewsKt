package com.stone.stoneviewskt.ui.materialdesign.collapsing

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentCollapsingBinding
import com.stone.stoneviewskt.util.showShort

/**
 * desc:    CollapsingToolbarLayout 折叠/展开 Toolbar
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/9 15:30
 */
class CollapsingFragment : BaseBindFragment<FragmentCollapsingBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentCollapsingBinding {
        return inflateBinding(inflater, container)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.fragmentCollapsingToolBar.title = "Stone-CollapsingFragment"
        mBind.fragmentCollapsingToolBar.logo = resources.getDrawable(R.mipmap.ic_launcher) //logo 会在导航图标之后
        mBind.fragmentCollapsingToolBar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_green_24dp)

        //Toolbar 导航按钮才有点击事件；还有就是 menu item。
        mBind.fragmentCollapsingToolBar.setNavigationOnClickListener {
            showShort("hei hei")
        }


        mBind.fragmentCollapsingCtl.setCollapsedTitleTextColor(Color.RED)
        mBind.fragmentCollapsingCtl.setExpandedTitleColor(Color.MAGENTA)
    }

}