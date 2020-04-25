package com.stone.stoneviewskt.ui.materialdesign.tabv

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/25 19:21
 */
class VpAdapter(private val pageTitleList: List<String>, private val list: List<VpFragment> = listOf(), fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): VpFragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        return pageTitleList[position]
//    }
}