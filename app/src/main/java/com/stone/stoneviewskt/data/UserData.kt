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

// 分页请求的数据响应，至少要返回一个总数量; page、pageIndex 都可本地定义，然后 可以计算出当前请求响应后，历史返回的总数量；最终计算出是否还有下一页
data class CustomerPageData(val totalCount: Int, val data: List<CustomerData>)

// base 分页数据类
open class BasePageData<T>(open val totalCount: Int, val data: List<T>)

data class CustomerData(val id: String, val name: String)


