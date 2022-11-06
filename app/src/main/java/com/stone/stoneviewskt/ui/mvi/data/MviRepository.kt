package com.stone.stoneviewskt.ui.mvi.data

class MviRepository(private val datasource: MviDatasource) {

    suspend fun getListMviData(page: Int, pageSize: Int):ArrayList<MviData> {
        return datasource.getListMviData(page, pageSize)
    }
}