package com.stone.stoneviewskt.recyclerview.multiviewype.data

import com.stone.stoneviewskt.ui.room.data.AddressData
import com.stone.stoneviewskt.ui.room.data.UserData

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/12/24 12:00
 */

data class AddressDataWrap(val address: AddressData, val viewType: Int = VIEW_TYPE_ADDRESS)

data class UserDataWrap(val user: UserData, val viewType: Int = VIEW_TYPE_USER)

const val VIEW_TYPE_ADDRESS = 1
const val VIEW_TYPE_USER = 2