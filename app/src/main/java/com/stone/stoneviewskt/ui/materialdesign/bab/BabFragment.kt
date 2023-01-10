package com.stone.stoneviewskt.ui.materialdesign.bab

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentBabBinding
import com.stone.stoneviewskt.util.logi

// BottomAppBar
class BabFragment  : BaseBindFragment<FragmentBabBinding>(R.layout.fragment_bab) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mBind.bottomBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.page_1 -> logi("click at page1")
                else -> logi("click other")
            }
            true
        }
    }
}