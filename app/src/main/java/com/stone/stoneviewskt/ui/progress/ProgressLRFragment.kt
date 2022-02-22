package com.stone.stoneviewskt.ui.progress

import android.os.Bundle
import android.widget.TextView
import com.stone.stoneviewskt.base.BaseFragment

class ProgressLRFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        val tv = view?.findViewById<TextView>(android.R.id.text1) ?: return
        tv.text = "打开进度弹窗"
        tv.setOnClickListener {
            // [0, 1]
            val data = ProgressData(-0.1f)
            ProgressLeftRightDialog(requireContext(), data.progressFloat)
        }
    }

    override fun getLayoutRes(): Int {
        return android.R.layout.test_list_item
    }
}