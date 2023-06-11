package com.stone.stoneviewskt.ui.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stone.stoneviewskt.BuildConfig
import com.stone.stoneviewskt.data.CustomerData
import com.stone.stoneviewskt.data.CustomerPageData
import com.stone.stoneviewskt.util.logi
import com.stone.stoneviewskt.util.showShort
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * desc:    分页数据源
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/6/10 11:26
 */
class PageListPageSource : PagingSource<Int, CustomerData>() {

    private var flagA = false
    private var flagB = false
    companion object {
        private var mockEmpty = true // 静态字段 模拟空数据
    }

    override fun getRefreshKey(state: PagingState<Int, CustomerData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CustomerData> {
        if (BuildConfig.DEBUG) { // 防止加载数据过快，loading 效果看不到
            delay(1000)
        }
        val currentPage = params.key ?: 1 // 当前页码
        val pageSize = params.loadSize // 每页多少条数据
        var bean: CustomerPageData? = null // 分页数据
        kotlin.runCatching { // 异常发生后，执行 onFailure {}
            // todo 真实开发时，移除模拟错误代码

            // 模拟 第2页 一定抛异常
            if (currentPage == 2 && !flagB) {
                flagA = true
            }
            if (flagA) {
                flagA = false
                flagB = true
                throw IllegalStateException("test-error")
            }


            if (currentPage % 2 == 0 && Random.nextInt() % 2 == 0) { // 当偶数页时，随机发生异常。  注意异常发生后，对请求的页码 -- currentPage是否有影响
                throw IllegalStateException("test-error")
            }
//            Api.createRetrofit(CustomerApiService::class.java).customerList(currentPage，pageSize) // 真实网络请求方式
            mockData(currentPage, pageSize)// todo 真实开发时，移除模拟数据代码
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

    // 模拟分页数据
    private fun mockData(pageIndex: Int, pageSize: Int): CustomerPageData? {
        if (mockEmpty) {
            mockEmpty = false
            return null
        }
        logi("分页参数：[pageIndex-$pageIndex] [pageSize-$pageSize]")
        val list = arrayListOf<CustomerData>()
        val totalCount = 55
        val currentCount = if (totalCount > pageIndex * pageSize) pageSize else totalCount - (pageIndex - 1) * pageSize
         (1..currentCount).forEach {
             val currentPosition = (pageIndex - 1) * pageSize + it
             list.add(CustomerData("id-$currentPosition", "name-$currentPosition"))
        }
        return CustomerPageData(totalCount, list)
    }
}