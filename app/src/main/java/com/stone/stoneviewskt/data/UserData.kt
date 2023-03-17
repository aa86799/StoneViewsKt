package com.stone.stoneviewskt.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * desc:    plugin: 'kotlin-android-extensions'   对应  kotlinx.android.parcel.Parcelize 。  kotlin-android-extensions-runtime-1.7.21.jar
 *          自动生成 Parcelable 的writeToParcel() 、describeContents() 、CREATOR 。
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/26 20:26
 */
@Parcelize
data class UserData(val name: String, val pwd: String, val isAdult: Boolean): Parcelable