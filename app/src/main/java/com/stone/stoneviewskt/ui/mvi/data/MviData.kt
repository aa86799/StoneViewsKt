package com.stone.stoneviewskt.ui.mvi.data

data class MviData(
    val id: Int,
    val title: String,
    val body: String,
    var bookmarked: Boolean = false
)