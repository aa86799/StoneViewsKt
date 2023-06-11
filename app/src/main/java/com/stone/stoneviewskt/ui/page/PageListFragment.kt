package com.stone.stoneviewskt.ui.page

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentPageListBinding
import com.stone.stoneviewskt.util.logi
import com.stone.stoneviewskt.util.showShort
import kotlinx.coroutines.flow.collectLatest

/**
 * desc:    Paging3分页+ConcatAdapter+空数据视图+下拉刷新(SwipeRefreshLayout)+加载更多+错误重试
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/6/10 11:09
 */
class PageListFragment: BaseBindFragment<FragmentPageListBinding>(R.layout.fragment_page_list) {

    private val viewModel by lazy { ViewModelProvider(this)[PageListViewModel::class.java] }
    private lateinit var concatAdapter: ConcatAdapter // 可合并 adapter
    private val adapter by lazy { PageListAdapter() }
    private val loadMoreAdapter by lazy {
        LoadMoreAdapter { // 点击 重试按钮
            adapter.retry()
        }
    }
    private val emptyAdapter: EmptyAdapter by lazy { EmptyAdapter() }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        concatAdapter = adapter.withLoadStateFooter(loadMoreAdapter) // 将 loadMoreAdapter 添加为 footer 效果
        emptyAdapter.updateData(listOf("")) // 向 emptyAdapter 添加一项，当其显示时，只显示一个对应视图
        mBind.rvPage.adapter = concatAdapter // 这时 已 添加了 loadMoreAdapter

        // 设置加载状态监听
        adapter.addLoadStateListener {
            logi("监听到loadState -- $it")
            // 如果 状态是 未加载(加载完成也算) 或 发生错误
            if (it.refresh is LoadState.NotLoading) {
                mBind.swipeRefresh.isRefreshing = false
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
                mBind.swipeRefresh.isRefreshing = false
            }
        }

        adapter.setItemClick { position, data ->
            showShort("点击查看 --- ${data.name}")
        }

        mBind.swipeRefresh.setOnRefreshListener {
            loadData()
        }

        mBind.swipeRefresh.isRefreshing = true
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenResumed {
            viewModel.loadData().collectLatest { // 加载数据
                adapter.submitData(it) // 绑定分页数据源
            }
        }
    }
}