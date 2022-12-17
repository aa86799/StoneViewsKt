package com.stone.stoneviewskt.common.mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.stone.stoneviewskt.common.BaseBindFragment
import kotlinx.coroutines.flow.Flow

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/4 11:46
 */
abstract class BaseMviFragment<VDB: ViewDataBinding>(@LayoutRes private val resId: Int): BaseBindFragment<VDB>(resId) {

    private val mBaseMviUi by lazy { BaseMviUi(requireContext(), this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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