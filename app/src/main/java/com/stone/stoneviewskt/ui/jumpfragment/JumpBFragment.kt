package com.stone.stoneviewskt.ui.jumpfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentJumpBBinding
import com.stone.stoneviewskt.util.logi

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
        info.append("fromFragment: ${arguments?.getString("fromFragment")}")
        mBind.tvJbInfo.text = info.toString()

        mBind.btnJbToA.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        mBind.btnJbToC.setOnClickListener {
            parentFragmentManager.commit {
                hide(this@JumpBFragment)
                add<JumpCFragment>(R.id.activity_jump_fragment_root, tag = "tagC", args = Bundle().apply {
                    putString("fromFragment", this@JumpBFragment::class.java.canonicalName)
                })
                addToBackStack(null)
            }
        }

        lifecycleScope.launchWhenResumed {
            parentFragmentManager.fragments.reversed().forEach { // 反序，从栈顶开始
                if (it is JumpFragment<*> && it.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    logi( "JumpBFragment: onResume: fragment: $it")
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        logi( "JumpBFragment: hidden: $hidden")
    }

    override fun onDestroy() {
        super.onDestroy()
        logi("destroy $this")
    }
}