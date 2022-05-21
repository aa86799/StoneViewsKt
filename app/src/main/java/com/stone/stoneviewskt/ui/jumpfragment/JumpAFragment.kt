package com.stone.stoneviewskt.ui.jumpfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.debounceClick
import com.stone.stoneviewskt.common.debounceClickWidthHandler
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentJumpABinding
import com.stone.stoneviewskt.util.logi

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/5/19 10:55
 */
class JumpAFragment : JumpFragment<FragmentJumpABinding>() {

    var mFlag = false
    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentJumpABinding {
        return inflateBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = StringBuffer()
        info.append("parent:").append(parentFragment).appendLine()
        info.append("parent fragmentManager:").append(parentFragmentManager).appendLine()
        info.append("activity fragmentManager:").append(requireActivity().supportFragmentManager).appendLine()
        mBind.tvJaInfo.text = info.toString()

        mBind.btnJaToB.setOnClickListener {
            //            childFragmentManager.commit { // 父容器的 container id，无法使用 child fragment manager
            requireActivity().supportFragmentManager.commit {
                add<JumpBFragment>(R.id.activity_jump_fragment_root, args = Bundle().apply {
//                putParcelable(SyncStateContract.Constants.KEY_DATA, mPickItem)
//                putParcelable(SyncStateContract.Constants.KEY_PICK_DETAIL, mPickDetail)
//                putBoolean(SyncStateContract.Constants.KEY_IS_FROM_PICK_CONTENT, true)
                })
                addToBackStack(null)
            }
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
    }


}