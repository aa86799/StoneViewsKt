package com.stone.stoneviewskt.ui.room.data

import androidx.room.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/4/4 14:47
 */
@Dao
interface AddressDao {
    //这里的 || 不是 或的意思，只是连接, 否则  :queryCondition 会报红。
    @Query("select * from address_data where add_name like '%'||:queryCondition||'%'")
    fun queryByCondition(queryCondition: String): List<AddressData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: AddressData)

    @Delete
    fun delete(address: AddressData): Int

    @Update
    fun update(address: AddressData): Int

    @Query("select * from address_data")
    fun queryList(): List<AddressData>

    @Query("select * from address_data where id = :id")
    fun queryItem(id: Long): AddressData


}