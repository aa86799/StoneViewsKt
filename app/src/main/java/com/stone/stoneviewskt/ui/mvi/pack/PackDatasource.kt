package com.stone.stoneviewskt.ui.mvi.pack

import com.stone.stoneviewskt.common.mvi.data.BaseResult
import com.stone.stoneviewskt.ui.mvi.data.MviData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class PackDatasource {

    suspend fun getListMviData(page: Int, pageSize: Int): Flow<BaseResult<ArrayList<MviData>?>> {
        return flow {
            val list = arrayListOf<MviData>()
            (0 until pageSize).forEach {
                list.add(MviData((page - 1) * pageSize + it, "page$page-title-$it", "body-$it", false))
            }
            if (Random.nextBoolean()) {
                throw Exception("random error ")
            }
            emit(BaseResult(list, true, ""))
        }
    }
}