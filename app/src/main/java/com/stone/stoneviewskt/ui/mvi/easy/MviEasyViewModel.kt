package com.stone.stoneviewskt.ui.mvi.easy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stone.stoneviewskt.ui.mvi.data.MviData
import com.stone.stoneviewskt.ui.mvi.data.MviDatasource
import com.stone.stoneviewskt.ui.mvi.data.MviRepository
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class MviEasyViewModel(private val repository: MviRepository = MviRepository(MviDatasource())) : ViewModel() {

    private val _state = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val state: StateFlow<MainUiState>
        get() = _state.asStateFlow()
//        get() {
//           return _state.asStateFlow()
//        }

    /**
     * 事件意图
     */
//    private val userIntent = MutableSharedFlow<MainIntent>()
    private val userIntent = MutableSharedFlow<MainIntent>(2, 3, BufferOverflow.SUSPEND)

    /**
     * 分发用户事件
     * @param viewAction
     */
    fun dispatch(viewAction: MainIntent) {
        viewModelScope.launch {
            userIntent.emit(viewAction)
        }
    }

    init {
        viewModelScope.launch {
            userIntent.distinctUntilChanged().collect {
                when (it) {
                    is MainIntent.Refresh -> refresh()
                    is MainIntent.LoadPageData -> loadPageData(it.page, it.pageSize)
                    else -> {}
                }
            }
        }
    }

    private fun refresh() {
        logi("refresh")
        loadPageData(1, 5)
    }

    // 加载分页数据
    private fun loadPageData(page: Int, pageSize: Int) {
        logi("loadPageData  $page")
        viewModelScope.launch {
            flow<MutableList<MviData>> {
                logi("emit data in ${Thread.currentThread().name}")
                val list = repository.getListMviData(page, pageSize)
                // 模拟正常 emit 数据，或发生了异常
                if (Random.nextBoolean())
                    throw Exception("An error has occurred")
                else emit(list)
            }.onStart {
                _state.value = MainUiState.Loading
            }.flowOn(Dispatchers.Default)
                .catch { e ->
                    _state.value = MainUiState.LoadError(e)
                }.collect {
                    _state.value = MainUiState.LoadSuccess(page, it)
                }
        }
    }
}

sealed class MainUiState {
    /**
     * 正在加载
     */
    object Loading : MainUiState() // 这个state，可以定义到 BaseState 中

    /**
     * 请求失败
     */
    data class LoadError(val error: Throwable) : MainUiState() // 这个state，可以定义到 BaseState 中

    /**
     * 请求成功
     * @param data 返回数据
     */
    data class LoadSuccess(val page: Int, val data: List<MviData>) : MainUiState()

}

sealed class MainIntent {
    object Refresh : MainIntent() // 刷新

    // Sealed types cannot be instantiated
    /*sealed*/ class LoadPageData(val page: Int, val pageSize: Int) : MainIntent() // 加载分页数据
}