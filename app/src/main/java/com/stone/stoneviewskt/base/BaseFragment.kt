package com.stone.stoneviewskt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 11:51
 */
abstract class BaseFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getLayoutView() ?: inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPreparedView(savedInstanceState)
    }

    fun getCurActivity() = _mActivity

    @LayoutRes
    protected open fun getLayoutRes(): Int = 0

    protected open fun getLayoutView(): View? {
        return null
    }

    protected open fun onPreparedView(savedInstanceState: Bundle?) {}
}