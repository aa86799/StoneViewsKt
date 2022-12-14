package com.stone.stoneviewskt.ui.room.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "UserData",
    foreignKeys = [ForeignKey(entity = AddressData::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("addressId"), onUpdate = ForeignKey.SET_NULL)],
    indices = [Index(value = ["addressId"])]) // 外键，需要 建立索引
data class UserData (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val pwd: String,
    val addressId: Int
)