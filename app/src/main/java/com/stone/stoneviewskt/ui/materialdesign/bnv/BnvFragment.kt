package com.stone.stoneviewskt.ui.materialdesign.bnv

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentBnvBinding
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.delay

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/10 19:12
 */
class BnvFragment : BaseBindFragment<FragmentBnvBinding>(R.layout.fragment_bnv) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        /*
         * 如果 setOnNavigationItemSelectedListener 对某个  itemId 没有相应操作，就不会有选中效果。
         */
        mBind.fragmentBnvView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    logi("page_1 clicked")
                    true
                }
                R.id.page_2 -> {
                    logi("page_2 clicked")
                    true
                }
                R.id.page_3 -> {
                    logi("page_3 clicked")
                    true
                }
                else -> false
            }
        }

        /* 再次/重复 选择 item 事件 */
        mBind.fragmentBnvView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.page_3 -> {
                    logi("page_3 clicked again")
                }
                R.id.page_4 -> {
                    logi("page_4 clicked again")
                }
            }
        }

        //默认选中
        mBind.fragmentBnvView.selectedItemId = R.id.page_3

        //默认 LABEL_VISIBILITY_AUTO， 选中后才展示 label
        mBind.fragmentBnvView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        //设置角标
        mBind.fragmentBnvView.getOrCreateBadge(R.id.page_1).run {
            isVisible = true
            number = 88
            backgroundColor = Color.GREEN
            badgeTextColor = Color.RED
        }
        mBind.fragmentBnvView.getOrCreateBadge(R.id.page_4).run {
            isVisible = true
            backgroundColor = Color.RED
            badgeTextColor = Color.WHITE
            number = 1000
            maxCharacterCount = 4 // number的位数 满足 maxCharacterCount 的设定，就会显示 9...9+
        }
        lifecycleScope.launchWhenResumed {
            delay(3000)
            mBind.fragmentBnvView.removeBadge(R.id.page_1) //移除 badge
        }
    }

}