package com.stone.stoneviewskt.ui.eraser

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentEraserBinding

class EraserFragment: BaseBindFragment<FragmentEraserBinding>(R.layout.fragment_eraser) {

    private lateinit var evImg: EraserView

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        evImg = mBind.evImg

        mBind.btnReset.setOnClickListener {
            evImg.reset()
        }

    }
}