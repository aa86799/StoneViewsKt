package com.stone.stoneviewskt.ui.page

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.util.logi

/**
 * desc:    分页 Adapter-UI 辅助类
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/7/5 09:20
 */
abstract class PageUiHelper<T : Any>(private val adapter: PagingDataAdapter<T, BaseViewHolder>) {

    private lateinit var concatAdapter: ConcatAdapter // 可合并 adapter
    val loadMoreAdapter by lazy {
        LoadMoreAdapter { // 点击 重试按钮
            adapter.retry()
        }
    }
    val emptyAdapter: EmptyAdapter by lazy { EmptyAdapter() }

    abstract fun onNotLoadingOrErrorState()

    fun init() {
        concatAdapter = adapter.withLoadStateFooter(loadMoreAdapter) // 将 loadMoreAdapter 添加为 footer 效果
        emptyAdapter.updateData(listOf("")) // 向 emptyAdapter 添加一项，当其显示时，只显示一个对应视图
        // 设置加载状态监听
        adapter.addLoadStateListener {
            logi("监听到loadState -- $it")
            // 如果 状态是 未加载(加载完成也算) 或 发生错误
            if (it.refresh is LoadState.NotLoading) {
                onNotLoadingOrErrorState()
                if (adapter.itemCount == 0) { // 真实数据为空
                    if (concatAdapter.adapters.contains(loadMoreAdapter)) {
                        concatAdapter.removeAdapter(loadMoreAdapter) // 移除加载更多
                        logi("removeAdapter -- loadMoreAdapter")
                    }
                    if (!concatAdapter.adapters.contains(emptyAdapter)) {
                        concatAdapter.addAdapter(emptyAdapter) // 添加空数据视图   仅在未添加过时才添加
                    }
                } else {
                    if (concatAdapter.adapters.contains(emptyAdapter)) {
                        concatAdapter.removeAdapter(emptyAdapter)
                        logi("removeAdapter -- emptyAdapter")
                        concatAdapter.addAdapter(loadMoreAdapter) // 只添加一次 loadMore
                    }
                }

            } else if (it.refresh is LoadState.Error) {
                onNotLoadingOrErrorState()
            }
        }
    }

    fun getMainAdapter() = concatAdapter
}