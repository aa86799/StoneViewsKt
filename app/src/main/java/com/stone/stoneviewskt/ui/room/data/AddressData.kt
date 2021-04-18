package com.stone.stoneviewskt.ui.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/4/4 14:46
 */
@Entity(tableName = "address_data")
data class AddressData(
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "add_name") var name: String,
    var phone: String,
    var address: String
)