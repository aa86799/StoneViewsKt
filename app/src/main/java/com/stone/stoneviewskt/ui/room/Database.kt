package com.stone.stoneviewskt.ui.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.stone.stoneviewskt.StoneApplication
import com.stone.stoneviewskt.ui.room.data.AddressDao
import com.stone.stoneviewskt.ui.room.data.AddressData
import com.stone.stoneviewskt.ui.room.data.UserDao
import com.stone.stoneviewskt.ui.room.data.UserData

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/4/4 12:33
 */
@Database(entities = [AddressData::class, UserData::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
    abstract fun userDao(): UserDao
}

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `UserData` " +
                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT, `pwd` TEXT, " +
                "`addressId` INTEGER, FOREIGN KEY(`addressId`) REFERENCES `address_data`(`id`))"
        )
    }

}

val appDatabase =
    Room.databaseBuilder(StoneApplication.instance, AppDatabase::class.java, "stoneDb")
        .addMigrations(MIGRATION_1_2)
        .allowMainThreadQueries() //允许在主线程 操作db
        .build()

