package com.stone.stoneviewskt.ui.sys

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentInstalledPkgBinding
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * desc:    获取已安装应用 包名，和应用名
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/30 14:17
 */
class InstalledAppPackageFragment: BaseBindFragment<FragmentInstalledPkgBinding>(R.layout.fragment_installed_pkg) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            mBind.tvContent.text = getInfo()
        }

        //todo rv显示，复制 包名
    }

    private suspend fun getInfo(): String {
        return withContext(Dispatchers.IO) {
            val builder = StringBuilder()
            requireContext().packageManager.getInstalledApplications(0).forEach { it ->
                // application name:  ${it.name}
                logi("${it.packageName} ")
                builder.append(it.packageName).append(" ")
                requireContext().packageManager.getApplicationLabel(it).let {
                    logi(it.toString())
                    builder.append(it).append("|||")
                }
                logi("------------")
            }
            builder.toString()
        }
    }
}