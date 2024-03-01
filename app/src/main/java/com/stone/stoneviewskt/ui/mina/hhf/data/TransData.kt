package com.stone.stoneviewskt.ui.mina.hhf.data

import java.io.Serializable

data class TransData(
    val data: String,
    val imgData: ByteArray,
    var date: String? = null
): Serializable