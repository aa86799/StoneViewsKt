package com.stone.stoneviewskt.ui.mvi.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MviData(
    val id: Int,
    val title: String,
    val body: String,
    var bookmarked: Boolean = false,
    val strId: String = UUID.randomUUID().toString()
): Parcelable