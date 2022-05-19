package com.stone.stoneviewskt.ui.jumpfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/5/19 16:04
 */
abstract class  JumpFragment<VB : ViewBinding> : Fragment() {

    protected var _mBinding: VB? = null
    protected val mBind: VB by lazy { _mBinding!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _mBinding = getViewBind(inflater, container, savedInstanceState)
        _mBinding?.root?.isClickable = true // 当前fragment触发的事件，当前来消费
        return _mBinding?.root
    }

    protected abstract fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): VB

    open fun onBackPressed(): Boolean = false

    protected fun addToStart(fragment: Fragment) {
        addToStart(android.R.id.content, fragment)
    }

    protected fun addToStart(@IdRes containerViewId: Int, fragment: Fragment) {
        parentFragmentManager.commit {
            add(containerViewId, fragment)
        }
    }
}