package com.stone.stoneviewskt.ui.mvi.pack

import com.stone.stoneviewskt.common.mvi.data.BaseResult
import com.stone.stoneviewskt.ui.mvi.data.MviData
import kotlinx.coroutines.flow.Flow

class PackRepository(private val datasource: PackDatasource) {

    suspend fun getListMviData(page: Int, pageSize: Int): Flow<BaseResult<ArrayList<MviData>?>> {
        return datasource.getListMviData(page, pageSize)
    }
}