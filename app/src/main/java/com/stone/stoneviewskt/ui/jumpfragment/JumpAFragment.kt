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
//            requireActivity().supportFragmentManager.commit {
            parentFragmentManager.commit {
                hide(this@JumpAFragment)
                add<JumpBFragment>(R.id.activity_jump_fragment_root, tag = "tagB", args = Bundle().apply {
                    putString("fromFragment", this@JumpAFragment::class.java.canonicalName)
                })
                addToBackStack(null)
            }
        }

        mBind.btnJaToC.setOnClickListener {
            parentFragmentManager.commit {
                remove(this@JumpAFragment)
                add<JumpCFragment>(R.id.activity_jump_fragment_root, tag = "tagC", args = Bundle().apply {
                    putString("fromFragment", this@JumpAFragment::class.java.canonicalName)
                })
            }
        }

        lifecycleScope.launchWhenResumed {
            requireActivity().supportFragmentManager.fragments.reversed().forEach { // 反序，从栈顶开始
                if (it is JumpFragment<*> && it.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    logi( "JumpAFragment: onResume: fragment: $it")
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        logi( "JumpAFragment: hidden: $hidden")
    }

    override fun onDestroy() {
        super.onDestroy()
        logi("destroy $this")
    }
}