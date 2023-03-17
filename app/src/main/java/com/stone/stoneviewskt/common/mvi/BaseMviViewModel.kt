package com.stone.stoneviewskt.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stone.stoneviewskt.BuildConfig
import com.stone.stoneviewskt.common.mvi.data.BaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/11/24 13:41
 */
abstract class BaseMviViewModel : ViewModel() {

    /**
     * UI 状态
     */
    private val _uiStateFlow by lazy { MutableStateFlow(initUiState()) }
    val uiStateFlow: StateFlow<IUiState> = _uiStateFlow.asStateFlow()

    /**
     * 事件意图, 点击事件、刷新等都是Intent。表示用户的主动操作
     */
    private val _userIntent = MutableSharedFlow<IUiIntent>()
    private val userIntent = _userIntent.asSharedFlow()

    init {
        viewModelScope.launch {
            userIntent.distinctUntilChanged().collect {
                handleUserIntent(it)
            }
        }
    }

    abstract fun handleUserIntent(intent: IUiIntent)

    protected open fun initUiState(): IUiState {
        return LoadingState(true)
    }

    protected fun sendUiState(block: IUiState.() -> IUiState) {
//    protected fun sendUiState(block: (IUiState) -> IUiState) {
//        _uiStateFlow.update { block(it) }
        _uiStateFlow.update { _uiStateFlow.value.block() }
    }

    /**
     * 分发意图
     *
     * 仅此一个 公开函数。供 V 调用
     */
    open fun dispatch(intent: IUiIntent) {
        viewModelScope.launch {
            _userIntent.emit(intent)
        }
    }

    /**
     * @param showLoading 是否展示Loading
     * @param request 请求数据
     * @param successCallback 请求成功
     * @param failCallback 请求失败，处理异常逻辑
     */
    protected fun <T : Any> requestDataWithFlow(
        showLoading: Boolean = true,
        request: suspend () -> Flow<BaseResult<T?>?>,
        successCallback: (T?) -> Unit,
        failCallback: suspend (String) -> Unit = { errMsg ->  //默认异常处理，子类可以进行覆写
            sendUiState { LoadErrorState(errMsg) }
        }
    ) {
        viewModelScope.launch {
            request()
                .onStart {
                    if (showLoading) {
                        sendUiState { LoadingState(true) }
                    }
                }
                .flowOn(Dispatchers.Default)
                .catch { // 代码运行异常
                    failCallback(if (it.message.isNullOrEmpty()) "发生了错误" else it.message!!)
                    sendUiState { LoadingState(false) }
                }
                .onCompletion {
                    // 如果发生了 异常，则可能多发送一次 LoadingState(false)
                    sendUiState { LoadingState(false) }
                }
                .flowOn(Dispatchers.Main)
                .collect {
                    if (BuildConfig.DEBUG) {
                        delay(1500) // 模拟用的
                    }
                    if (it?.success == true) {
                        successCallback(it.data)
                    } else {
                        failCallback(if (it?.message.isNullOrEmpty()) "发生了错误" else it?.message!!)
                    }
                }
        }
    }
}