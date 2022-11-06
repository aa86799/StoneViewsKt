package com.stone.stoneviewskt.ui.mvi.data

class MviDatasource {

    suspend fun getListMviData(page: Int, pageSize: Int): ArrayList<MviData> {
        val list = arrayListOf<MviData>()
        (0 until pageSize).forEach {
            list.add(MviData((page - 1) * pageSize + it, "page$page-title-$it", "body-$it", false))
        }
        return list
    }
}