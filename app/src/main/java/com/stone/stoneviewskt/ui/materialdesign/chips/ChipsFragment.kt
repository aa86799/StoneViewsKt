package com.stone.stoneviewskt.ui.materialdesign.chips

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentChipsBinding
import com.stone.stoneviewskt.util.showShort

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/11 20:24
 */
class ChipsFragment : BaseBindFragment<FragmentChipsBinding>(R.layout.fragment_chips) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.fragmentChipsEntry.setOnCloseIconClickListener {
            showShort("click close icon")
        }
        mBind.fragmentChipsChoice.setOnCheckedChangeListener { buttonView, isChecked ->
            showShort("$isChecked")
        }
    }

}