package com.stone.stoneviewskt.ui.mvi.easy.datasource

import com.stone.stoneviewskt.ui.mvi.data.MviData

class MviRepository(private val datasource: MviDatasource) {

    suspend fun getListMviData(page: Int, pageSize: Int):ArrayList<MviData> {
        return datasource.getListMviData(page, pageSize)
    }
}