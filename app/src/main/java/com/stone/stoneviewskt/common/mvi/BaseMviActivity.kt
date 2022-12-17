package com.stone.stoneviewskt.common.mvi

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.stone.stoneviewskt.common.BaseBindActivity
import kotlinx.coroutines.flow.Flow

/*
 * 可以组合多个 ViewModel，所以没有定义 成泛型
 */
abstract class BaseMviActivity<VB : ViewDataBinding>(@LayoutRes resId: Int): BaseBindActivity<VB>(resId) {

    private val mBaseMviUi by lazy { BaseMviUi(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
        initObserver()
        initBiz()
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initObserver()

    abstract fun initBiz()

    /**
     * 显示用户等待框
     * @param msg 提示信息
     */
    protected fun showLoadingDialog(msg: String = "请等待...") {
        mBaseMviUi.showLoadingDialog(msg)
    }

    /**
     * 隐藏等待框
     */
    protected fun dismissLoadingDialog() {
        mBaseMviUi.dismissLoadingDialog()
    }

    protected fun showToast(msg: String) {
        mBaseMviUi.showToast(msg)
    }

    protected fun showToastLong(msg: String) {
        mBaseMviUi.showToastLong(msg)
    }

    protected fun stateFlowHandle(flow: Flow<IUiState>, handleError: Boolean = true, block: (state: IUiState) -> Unit) {
        mBaseMviUi.stateFlowHandle(flow, handleError, block)
    }
}