package com.stone.stoneviewskt.ui.dialog

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_dialog_manager.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/1/24 14:19
 */
class MainDialogManagerFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_mdm_life.setOnClickListener {
            TestDialogFragment().show(childFragmentManager, "TestDialogFragment")
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main_dialog_manager
    }
}