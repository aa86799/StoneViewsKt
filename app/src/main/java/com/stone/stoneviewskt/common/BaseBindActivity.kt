package com.stone.stoneviewskt.common

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.stone.stoneviewskt.base.BaseActivity

abstract class BaseBindActivity<VB : ViewDataBinding>(@LayoutRes resId: Int): BaseActivity() {

    protected val mBind: VB by lazy { DataBindingUtil.setContentView(this, resId) }
    protected val _bind: VB? = mBind

}