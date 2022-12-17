package com.stone.stoneviewskt.ui.jumpfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentJumpBBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/5/19 10:55
 */
class JumpBFragment : JumpFragment<FragmentJumpBBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentJumpBBinding {
        return inflateBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = StringBuffer()
        info.append("parent:").append(parentFragment).appendLine()
        info.append("parent fragmentManager:").append(parentFragmentManager).appendLine()
        info.append("activity fragmentManager:").append(requireActivity().supportFragmentManager).appendLine()
        mBind.tvJbInfo.text = info.toString()

        mBind.btnJbToA.setOnClickListener {
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

        mBind.btnJbToC.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().supportFragmentManager.fragments.reversed().forEach { // 反序，从栈顶开始
            if (it is JumpFragment<*> && it.lifecycle.currentState == Lifecycle.State.RESUMED) {
                Log.i("TAG", "onResume: fragment: $it")
                return
            }
        }
    }
}