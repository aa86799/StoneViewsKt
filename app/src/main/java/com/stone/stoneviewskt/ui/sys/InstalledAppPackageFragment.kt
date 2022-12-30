package com.stone.stoneviewskt.ui.sys

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentInstalledPkgBinding
import com.stone.stoneviewskt.util.logi

/**
 * desc:    获取已安装应用 包名，和应用名
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/30 14:17
 */
class InstalledAppPackageFragment: BaseBindFragment<FragmentInstalledPkgBinding>(R.layout.fragment_installed_pkg) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        requireContext().packageManager.getInstalledApplications(0).forEach {
            // application name:  ${it.name}
            logi("${it.packageName} ")
            requireContext().packageManager.getApplicationLabel(it).let {
                logi(it.toString())
            }
            logi("------------")
        }
        //todo rv显示，复制 包名
    }
}