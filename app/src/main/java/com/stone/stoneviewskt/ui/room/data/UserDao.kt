package com.stone.stoneviewskt.ui.room.data

import androidx.room.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/13 16:17
 */
@Dao
interface UserDao {
    //这里的 || 不是 或的意思，只是连接, 否则  :queryCondition 会报红。
    @Query("select * from UserData where username like '%'||:queryCondition||'%'")
    fun queryByCondition(queryCondition: String): List<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: UserData)

    @Delete
    fun delete(address: UserData): Int

    @Update
    fun update(address: UserData): Int

    @Query("select * from UserData")
    fun queryList(): List<UserData>

    @Query("select * from UserData where id = :id")
    fun queryItem(id: Long): UserData


}