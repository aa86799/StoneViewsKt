package com.stone.stoneviewskt.service.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServiceRequest(
    var data: String
): Parcelable