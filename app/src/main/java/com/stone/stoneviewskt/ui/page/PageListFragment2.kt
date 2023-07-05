package com.stone.stoneviewskt.ui.page

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.data.CustomerData
import com.stone.stoneviewskt.databinding.FragmentPageListBinding
import com.stone.stoneviewskt.util.showShort
import kotlinx.coroutines.flow.collectLatest

/**
 * desc:    Paging3分页+ConcatAdapter+空数据视图+下拉刷新(SwipeRefreshLayout)+加载更多+错误重试
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/6/10 11:09
 */
class PageListFragment2: BaseBindFragment<FragmentPageListBinding>(R.layout.fragment_page_list) {

    private val viewModel by lazy { ViewModelProvider(this)[PageListViewModel::class.java] }
    private val adapter by lazy { PageListAdapter() }
    private val helper by lazy {
        object : PageUiHelper<CustomerData>(adapter) {
            override fun onNotLoadingOrErrorState() {
                mBind.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        helper.init()
        mBind.rvPage.adapter = helper.getMainAdapter() // 这时 已 添加了 loadMoreAdapter

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