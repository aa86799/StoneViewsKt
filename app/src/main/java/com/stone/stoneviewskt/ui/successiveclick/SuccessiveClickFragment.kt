package com.stone.stoneviewskt.ui.successiveclick

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentSuccessiveClickBinding
import com.stone.stoneviewskt.util.logi

class SuccessiveClickFragment: BaseBindFragment<FragmentSuccessiveClickBinding>(R.layout.fragment_successive_click) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mBind.btnClickBus.setOnClickListener {
            if (ButtonFastClickUtils.isFastDoubleClick()) return@setOnClickListener
            logi("btnClickBus ${System.currentTimeMillis()}")
        }
    }
}