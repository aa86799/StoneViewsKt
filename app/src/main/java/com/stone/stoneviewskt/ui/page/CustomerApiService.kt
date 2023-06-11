package com.stone.stoneviewskt.ui.page

import com.stone.stoneviewskt.data.BasePageData
import com.stone.stoneviewskt.data.CustomerData
import com.stone.stoneviewskt.data.CustomerPageData
import retrofit2.http.GET
import retrofit2.http.Query

interface GasCustomerApiService {

    /**
     * 用户列表
     */
    @GET("/api/customerList")
    suspend fun customerList(@Query("pageIndex") pageIndex: Int, @Query("pageSize") pageSize: Int): CustomerPageData?

    suspend fun customerList2(@Query("pageIndex") pageIndex: Int, @Query("pageSize") pageSize: Int): BasePageData<CustomerData>?

}