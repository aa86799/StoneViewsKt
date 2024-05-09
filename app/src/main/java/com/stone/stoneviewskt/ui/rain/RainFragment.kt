package com.stone.stoneviewskt.ui.rain

import android.view.View
import com.stone.stoneviewskt.base.BaseFragment

class RainFragment: BaseFragment() {

    private lateinit var view: CodeRainView

    override fun getLayoutRes(): Int {
        return 0
    }

    override fun getLayoutView(): View? {
        view = CodeRainView(requireActivity())
        return view
    }
}