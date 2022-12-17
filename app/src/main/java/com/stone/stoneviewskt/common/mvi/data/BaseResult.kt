package com.stone.stoneviewskt.common.mvi.data

data class BaseResult<T>(
    var data: T? = null,
    var success: Boolean? = null,
    var message: String? = null
)
