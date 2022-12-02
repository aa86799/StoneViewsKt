package com.stone.stoneviewskt.ui.successiveclick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentSuccessiveClickBinding
import com.stone.stoneviewskt.util.logi

class SuccessiveClickFragment: BaseBindFragment<FragmentSuccessiveClickBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentSuccessiveClickBinding {
        return inflateBinding(inflater, container)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mBind.btnClickBus.setOnClickListener {
            if (ButtonFastClickUtils.isFastDoubleClick()) return@setOnClickListener
            logi("btnClickBus ${System.currentTimeMillis()}")
        }
    }
}