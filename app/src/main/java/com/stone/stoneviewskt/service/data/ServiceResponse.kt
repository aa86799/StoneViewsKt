package com.stone.stoneviewskt.service.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceResponse(
    var data: String
): Parcelable