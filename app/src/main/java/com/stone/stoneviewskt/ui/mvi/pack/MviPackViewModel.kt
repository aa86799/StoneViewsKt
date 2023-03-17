package com.stone.stoneviewskt.ui.mvi.pack

import com.stone.stoneviewskt.common.mvi.*

class MviPackViewModel(private val repository: PackRepository = PackRepository(PackDatasource())) : BaseMviViewModel() {

    companion object {
        private const val PAGE_SIZE = 2
    }

    override fun handleUserIntent(intent: IUiIntent) {
        when (intent) {
            is InitDataIntent, is PackIntent.Refresh -> loadPageData(1) // 初始化 或 刷新，都加载第1页
            is PackIntent.LoadPageData -> loadPageData(intent.page)
        }
    }

    private fun loadPageData(page: Int) {
        requestDataWithFlow(request = { repository.getListMviData(page, PAGE_SIZE) }, successCallback = {
            sendUiState {
                LoadSuccessPageDataState(if (page == 1) PackUiState.RefreshPageDataSuccess() else PackUiState.LoadPageDataSuccess(), it, page)
            }
        }, failCallback = {
            sendUiState {
                LoadErrorState("操作失败：$it")
            }
        })
    }

}

sealed class PackUiState {
    class LoadPageDataSuccess : IUiState
    class RefreshPageDataSuccess : IUiState
}

sealed class PackIntent {
    /*
     * 尽量不要用 object 定义 意图类；每次从 UI层 发送来的意图，正常情况下都是要响应的；
     * 而使用 object 单例后，和 sharedStateFlow 的 distinctUntilChanged() 本意有冲突了：
     * 连续发送单例事件 非首次的、后续的 就被过滤掉了。
     */
    class Refresh(val page: Int = 1) : IUiIntent // 刷新

    class LoadPageData(val page: Int) : IUiIntent // 加载分页数据
}