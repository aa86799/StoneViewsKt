package com.stone.stoneviewskt.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentMainDialogManagerBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/1/24 14:19
 */
class MainDialogManagerFragment : BaseBindFragment<FragmentMainDialogManagerBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainDialogManagerBinding {
        return inflateBinding(inflater, container)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.fragmentMdmLife.setOnClickListener {
            TestDialogFragment().show(childFragmentManager, "TestDialogFragment")
        }
    }
}