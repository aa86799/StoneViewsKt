package com.stone.stoneviewskt.ui.dialog

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentMainDialogManagerBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/1/24 14:19
 */
class MainDialogManagerFragment : BaseBindFragment<FragmentMainDialogManagerBinding>(R.layout.fragment_main_dialog_manager) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.fragmentMdmLife.setOnClickListener {
            TestDialogFragment().show(childFragmentManager, "TestDialogFragment")
        }
    }
}