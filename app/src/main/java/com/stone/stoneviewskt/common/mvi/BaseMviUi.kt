package com.stone.stoneviewskt.common.mvi

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.stone.stoneviewskt.common.Weak
import com.stone.stoneviewskt.util.logi
import com.stone.stoneviewskt.util.stoneToast
import kotlinx.coroutines.flow.Flow

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/4 11:24
 */
class BaseMviUi(private val context: Context, private val lifecycleOwner: LifecycleOwner) {

    private var mLoading by Weak<LoadingDialog>()

    fun stateFlowHandle(flow: Flow<IUiState>, handleError: Boolean, block: (state: IUiState) -> Unit) {
        lifecycleOwner.lifecycleScope.launchWhenCreated { // 开启新的协程
            // repeatOnLifecycle 是一个挂起函数；低于目标生命周期状态会取消协程，内部由suspendCancellableCoroutine实现
            // STATE.CREATED 低于 STARTED 状态；若因某种原因，界面重建，重走 Activity#onCreate 生命周期，就会取消该协程，直到 STARTED 状态之后，被调用者重新触发
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {
                    when (it) {
                        is LoadingState -> { if (it.isShow) showLoadingDialog() else dismissLoadingDialog() }
                        is LoadErrorState -> if (handleError) showToast(it.error) else block(it)
                        else -> block(it)
                    }
                }
            }
        }
    }

    /**
     * 显示用户等待框
     * @param msg 提示信息
     */
    fun showLoadingDialog(msg: String = "请等待...") {
        logi("show loading view")
            if (mLoading?.isShowing == true) {
                mLoading?.setLoadingMsg(msg)
            } else {
                mLoading = LoadingDialog(context)
                mLoading?.setLoadingMsg(msg)
                mLoading!!.show()
            }
    }

    /**
     * 隐藏等待框
     */
    fun dismissLoadingDialog() {
        logi("dismiss LoadingDialog")
        if (mLoading?.isShowing == true) {
            mLoading?.dismiss()
        }
    }

    fun showToast(msg: String) {
            stoneToast(context, msg)
    }

    fun showToastLong(msg: String) {
            stoneToast(context, msg, Toast.LENGTH_LONG)
    }

}