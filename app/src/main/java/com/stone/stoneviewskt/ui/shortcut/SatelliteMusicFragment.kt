package com.stone.stoneviewskt.ui.shortcut

import android.view.View
import android.widget.TextView
import com.stone.stoneviewskt.base.BaseFragment

class SatelliteMusicFragment: BaseFragment() {

    override fun getLayoutView(): View? {
        val tv = TextView(requireContext())
        tv.text = "SatelliteMusicFragment"
        return tv
    }
}