package com.stone.stoneviewskt.ui.materialdesign.chips

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.showShort
import kotlinx.android.synthetic.main.fragment_chips.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/11 20:24
 */
class ChipsFragment: BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_chips_entry.setOnClickListener {

        }
        fragment_chips_entry.setOnCloseIconClickListener {
            showShort("click close icon")
        }
        fragment_chips_choice.setOnCheckedChangeListener { buttonView, isChecked ->
            showShort("$isChecked")
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_chips
    }
}