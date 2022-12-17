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
import com.stone.stoneviewskt.databinding.FragmentJumpCBinding
import com.stone.stoneviewskt.util.logi

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/5/19 10:55
 */
class JumpCFragment : JumpFragment<FragmentJumpCBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentJumpCBinding {
        return inflateBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = StringBuffer()
        info.append("parent:").append(parentFragment).appendLine()
        info.append("parent fragmentManager:").append(parentFragmentManager).appendLine()
        info.append("activity fragmentManager:").append(requireActivity().supportFragmentManager).appendLine()
        info.append("fromFragment: ${arguments?.getString("fromFragment")}")
        mBind.tvJcInfo.text = info.toString()

        mBind.btnJcToA.setOnClickListener {
//            val fa = parentFragmentManager.fragments.find { it is JumpAFragment }
            val fa = parentFragmentManager.findFragmentByTag("tagA")
            if (fa?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                // 栈里只有一个 fragment 时，popBackStack() 是没有效果的
                parentFragmentManager.popBackStack()
                parentFragmentManager.popBackStack()
            } else {
                parentFragmentManager.commit {
                    remove(this@JumpCFragment)
                    add<JumpAFragment>(R.id.activity_jump_fragment_root, args = Bundle().apply {
                        putString("fromFragment", this@JumpCFragment::class.java.canonicalName)
                    })
                }
            }
        }

        mBind.btnJcToB.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        lifecycleScope.launchWhenResumed {
            requireActivity().supportFragmentManager.fragments.reversed().forEach { // 反序，从栈顶开始
                if (it is JumpFragment<*> && it.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    logi( "JumpCFragment: onResume: fragment: $it")
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        logi( "JumpCFragment: hidden: $hidden")
    }

    override fun onDestroy() {
        super.onDestroy()
        logi("destroy $this")
    }
}