package com.stone.stoneviewskt.service.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServiceResponse(
    var data: String
): Parcelable