package com.stone.stoneviewskt.ui.mvi.data

import java.util.*

data class MviData(
    val id: Int,
    val title: String,
    val body: String,
    var bookmarked: Boolean = false,
    val strId: String = UUID.randomUUID().toString()
)