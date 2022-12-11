package com.stone.stoneviewskt.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.stone.stoneviewskt.base.BaseFragment

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/5/19 12:54
 */
abstract class BaseBindFragment<VB : ViewDataBinding>(@LayoutRes private val resId: Int) : BaseFragment() {

    protected lateinit var mBind: VB
    protected var _bind: VB? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBind =  DataBindingUtil.inflate(inflater, resId, container, false)
        _bind = mBind
        mBind.root.isClickable = true // 当前fragment触发的事件，当前来消费
        return mBind.root
    }

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