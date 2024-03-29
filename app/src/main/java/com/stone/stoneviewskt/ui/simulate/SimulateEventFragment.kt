package com.stone.stoneviewskt.ui.simulate

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentSimulateEventBinding
import com.tencent.mmkv.MMKV

/**
 * desc:    模拟点击
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/10/27
 */
class SimulateEventFragment: BaseBindFragment<FragmentSimulateEventBinding>(R.layout.fragment_simulate_event) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        // activity.windowManager.defaultDisplay.width
        activity ?: return
        MMKV.defaultMMKV().encode("dw", activity!!.resources.displayMetrics.widthPixels)
        MMKV.defaultMMKV().encode("dh", activity!!.resources.displayMetrics.heightPixels)

        mBind.btnPermission.setOnClickListener {
            // 检查并跳转到辅助功能界面，提示用户打开辅助功能开关。
            try {
                activity?.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            } catch (e: Exception) {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }
        }
    }
}