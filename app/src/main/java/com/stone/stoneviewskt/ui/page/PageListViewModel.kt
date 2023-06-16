package com.stone.stoneviewskt.ui.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stone.stoneviewskt.data.CustomerData
import kotlinx.coroutines.flow.Flow

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/6/10 12:12
 */
class PageListViewModel : ViewModel() {

    // todo 截止到 3.1.1版本，官方库有bug：超出 initialLoadSize 所在页码后， 再retry 没问题；反之retry，会数据重复
    // todo 为了规避 建议 真实开发时，initialLoadSize 和 pageSize 的值相同。 或看看后续版本是否有修复

    fun loadData(): Flow<PagingData<CustomerData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 15, // 初始取的 pageSize，通常要大于指定的 pageSize   但大于了，在retry时又会出现bug
                prefetchDistance = 1   // prefetchDistance条数据 时加载下一页
            ),
            // 每页都会调用一次 pagingSource#load(), 请求数据
//            pagingSourceFactory = { PageListPageSource2() }
            pagingSourceFactory = { PageListPageSource() }
        ).flow.cachedIn(viewModelScope) // 设置缓存
    }
}