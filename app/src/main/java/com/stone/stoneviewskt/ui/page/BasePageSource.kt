package com.stone.stoneviewskt.ui.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stone.stoneviewskt.data.BasePageData
import com.stone.stoneviewskt.util.logi
import com.stone.stoneviewskt.util.showShort

/**
 * desc:    base 分页数据源
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/6/11 13:05
 */
abstract class BasePageSource<T: Any> : PagingSource<Int, T>() {


    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    abstract fun loadData(pageIndex: Int, pageSize: Int): BasePageData<T>?

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentPage = params.key ?: 1 // 当前页码
        val pageSize = params.loadSize // 每页多少条数据
        var bean: BasePageData<T>? = null // 分页数据
        kotlin.runCatching { // 异常发生后，执行 onFailure {}
            loadData(currentPage, pageSize)
        }.onSuccess {
            bean = it
        }.onFailure {
            it.printStackTrace()
            if (it !is java.util.concurrent.CancellationException) {
                showShort("出错了" + it.message)
                return LoadResult.Error(it) // 如果返回 error，那后续 加载更多将不会触发
            }
        }
        val prevKey = if (currentPage > 1) currentPage - 1 else null // 当前页不是第一页的话，前一页为当前页减一
        val hasMore = currentPage * pageSize < (bean?.totalCount ?: 0)
        val nextKey = if (hasMore) currentPage + 1 else null // 当前页有数据的话，下一页就是，当前页加一，反之为空
        logi("currentPage:$currentPage    pageSize:$pageSize    prevKey:$prevKey     nextKey:$nextKey")
        return try {
            LoadResult.Page(
                data = bean?.data ?: arrayListOf(), // 数据
                prevKey = prevKey, // 为当前页的前一页
                nextKey = nextKey  // 为当前页的下一页
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}