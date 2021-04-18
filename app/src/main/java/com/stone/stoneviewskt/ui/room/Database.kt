package com.stone.stoneviewskt.ui.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stone.stoneviewskt.StoneApplication
import com.stone.stoneviewskt.ui.room.data.AddressDao
import com.stone.stoneviewskt.ui.room.data.AddressData

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/4/4 12:33
 */
@Database(entities = [AddressData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}

val addDatabase =
    Room.databaseBuilder(StoneApplication.instance, AppDatabase::class.java, "stoneDb")
        .allowMainThreadQueries() //允许在主线程 操作db
        .build()

