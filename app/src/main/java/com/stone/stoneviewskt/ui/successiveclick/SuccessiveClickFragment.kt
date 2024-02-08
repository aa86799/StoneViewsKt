package com.stone.stoneviewskt.ui.successiveclick

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.clickWithTrigger
import com.stone.stoneviewskt.common.debounceClick
import com.stone.stoneviewskt.common.debounceClickWidthHandler
import com.stone.stoneviewskt.databinding.FragmentSuccessiveClickBinding
import com.stone.stoneviewskt.util.logi

/**
 * desc:    防连续点击示例
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/2
 */
class SuccessiveClickFragment: BaseBindFragment<FragmentSuccessiveClickBinding>(R.layout.fragment_successive_click) {

    private var mFlag = false

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mBind.btnClickBus.setOnClickListener {
            if (ButtonFastClickUtils.isFastDoubleClick()) return@setOnClickListener
            logi("btnClickBus ${System.currentTimeMillis()}")
        }

        mBind.btnJaClickA.debounceClick(lifecycleScope) {
            Thread {
                logi("sleep start")
                Thread.sleep(2000)
                logi("sleep end")
                mFlag = !mFlag
                mBind.btnJaClickA.post { // 主线程刷新图片
                    // 在end 之后， refresh 之前，退出界面， back退出和home退出，都没问题
                    // 可能的问题就是，异常情况，引起了界面重建， mBind、view 为null
                    logi("image refresh")
                    mBind.ivImg.setImageResource(if (mFlag) R.drawable.kotlin else R.drawable.satellite_button)
                }
            }.start()
        }
        mBind.btnJaClickB.debounceClick(this) { }
        mBind.btnJaClickC.debounceClick(lifecycleScope, originBlock = { })
        mBind.btnJaClickD.debounceClick(this, originBlock = { })

        mBind.btnJaClickE.debounceClickWidthHandler { }
        mBind.btnJaClickF.debounceClickWidthHandler(originBlock = { })

        mBind.btnJaClickG.clickWithTrigger {
            Thread {
                logi("sleep start")
                Thread.sleep(2000)
                logi("sleep end")
                mFlag = !mFlag
                mBind.btnJaClickA.post { // 主线程刷新图片
                    // 在end 之后， refresh 之前，退出界面， back退出和home退出，都没问题
                    // 可能的问题就是，异常情况，引起了界面重建， mBind、view 为null
                    logi("image refresh")
                    mBind.ivImg.setImageResource(if (mFlag) R.drawable.kotlin else R.drawable.satellite_button)
                }
            }.start()
        }

        mBind.btnJaClickH.clickWithTrigger(originBlock = {
            logi("btnJaClickG")
        })
    }
}