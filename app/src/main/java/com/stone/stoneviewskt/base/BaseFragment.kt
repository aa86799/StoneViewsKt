package com.stone.stoneviewskt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 11:51
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getLayoutView() ?: inflater.inflate(getLayoutRes(), container, false)
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected open fun getLayoutView(): View? {
        return null
    }
}