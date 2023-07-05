package com.stone.stoneviewskt.ui.page

import com.stone.stoneviewskt.BuildConfig
import com.stone.stoneviewskt.data.BasePageData
import com.stone.stoneviewskt.data.CustomerData
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.delay

/**
 * desc:    分页数据源
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/6/11 13:11
 */
class PageListPageSource2 : BasePageSource<CustomerData>() {

    override suspend fun loadData(pageIndex: Int, pageSize: Int): BasePageData<CustomerData>? {
        if (BuildConfig.DEBUG) { // 防止加载数据过快，loading 效果看不到
            delay(1000)
        }
        return mockData(pageIndex, pageSize)
    }

    // 模拟分页数据
    private fun mockData(pageIndex: Int, pageSize: Int): BasePageData<CustomerData> {
        logi("分页参数：[pageIndex-$pageIndex] [pageSize-$pageSize]")
        val list = arrayListOf<CustomerData>()
        val totalCount = 55
        val currentCount = if (totalCount > pageIndex * pageSize) pageSize else totalCount - (pageIndex - 1) * pageSize
         (1..currentCount).forEach {
             val currentPosition = (pageIndex - 1) * pageSize + it
             list.add(CustomerData("id-$currentPosition", "name-$currentPosition"))
        }
        return BasePageData(totalCount, list)
    }

}