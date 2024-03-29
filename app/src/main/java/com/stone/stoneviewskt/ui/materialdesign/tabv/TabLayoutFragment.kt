package com.stone.stoneviewskt.ui.materialdesign.tabv

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentTabLayoutBinding
import org.jetbrains.anko.textColor


/**
 * desc:    TabLayout + ViewPager
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/25 13:32
 */
class TabLayoutFragment : BaseBindFragment<FragmentTabLayoutBinding>(R.layout.fragment_tab_layout) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mBind.fragmentTabLayoutTitle.mIvLeft.setOnClickListener {
            _mActivity.finish()
        }

        for (tab in TABS) {
            mBind.fragmentTabLayoutTl.addTab(mBind.fragmentTabLayoutTl.newTab().setText(tab))

        }
        mBind.fragmentTabLayoutTl2.addTab(mBind.fragmentTabLayoutTl2.newTab().setText("A"))
        //设置右上角，角标；类似小红点效果
        mBind.fragmentTabLayoutTl2.getTabAt(0)?.orCreateBadge?.let {
            it.isVisible = true
            it.badgeTextColor = Color.YELLOW
            it.backgroundColor = Color.RED
            it.number = 99
//            it.clearNumber()
        }

        val list = mutableListOf<VpFragment>()
        (1..10).forEach {
            list.add(VpFragment().apply {
                arguments = Bundle().apply { putString(VpFragment.KEY_DATA, "NO.$it") }
            })
        }
        val pageTitleList = (1..10).toList().map { "$it" }
        fragmentManager ?: return
        mBind.fragmentTabLayoutVp.adapter = VpAdapter(pageTitleList, list, requireFragmentManager())
        mBind.fragmentTabLayoutTl3.setupWithViewPager(mBind.fragmentTabLayoutVp)

        pageTitleList.indices.forEach {
            mBind.fragmentTabLayoutTl3.getTabAt(it)?.customView = makeTabView(pageTitleList, it)
        }
        mBind.fragmentTabLayoutTl3.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                ((tab.customView as ViewGroup).getChildAt(0) as TextView).textColor = Color.BLACK
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                ((tab.customView as ViewGroup).getChildAt(0) as TextView).textColor = Color.GREEN
            }
        })

        //通常，设置某个position的view的选中状态； 但若是第0个，无效， 这是个bug。
//        fragment_tab_layout_tl3.getTabAt(0)?.customView?.isSelected = true
//        fragment_tab_layout_tl3.getTabAt(0)?.select()
        //强行设置第0个
        ((mBind.fragmentTabLayoutTl3.getTabAt(0)?.customView as ViewGroup).getChildAt(0) as TextView).textColor = Color.GREEN
    }

    private fun makeTabView(titles: List<String>, position: Int): View {
        //如下居然没效果，在测试机上，见鬼了
        val tabView: View = View.inflate(context, R.layout.layout_tab_icon, null)
        val textView: TextView = tabView.findViewById(R.id.layout_tab_icon_tv)
        val imageView: ImageView = tabView.findViewById(R.id.layout_tab_icon_iv)
        textView.text = titles[position]
        imageView.setImageResource(R.mipmap.satellite_thought)
        return tabView

//        val layout = LinearLayout(context)
//        layout.orientation = LinearLayout.VERTICAL
//        val tv = TextView(context)
//        tv.text = titles[position]
//        tv.gravity = Gravity.CENTER
//        tv.backgroundColor = if (position %2 == 0) Color.parseColor("#d2d2d2") else Color.MAGENTA
//        layout.addView(tv)
//
//        val iv = ImageView(context)
//        iv.imageResource = if (position %2 == 0) R.mipmap.satellite_thought else R.mipmap.satellite_music
//        layout.addView(iv)
//        return layout
    }

    companion object {
        private val TABS = (1..10).map { "$it" }
    }

}