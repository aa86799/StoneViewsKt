package com.stone.stoneviewskt.data

import androidx.annotation.WorkerThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.stone.stoneviewskt.StoneApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/27 08:52
 */
class SCaches {

    companion object {
        private const val PREFERENCE_NAME = "Stone_DataStore"

        //use PreferenceDataStoreFactory   not DataStoreFactory
        private val dataStore: DataStore<Preferences> = StoneApplication.instance.createDataStore(name = PREFERENCE_NAME)

        //迁移 SharedPreferences 到 DataStore
        private var dataStore2: DataStore<Preferences> = StoneApplication.instance.createDataStore(
                name = PREFERENCE_NAME,
                migrations = listOf(SharedPreferencesMigration(StoneApplication.instance, "sp", setOf("key1", "key2")))
        )

        suspend fun first() {
            dataStore.data.first()
        }

        @WorkerThread
        suspend fun getString(name: String): Flow<String?> {
            return dataStore.data
                    /*.catch {
                        if (it is IOException) {
                            it.printStackTrace()
                            emit(emptyPreferences())
                        } else {
                            throw it
                        }
                    }*/.map {
                        it[preferencesKey(name)]
                    }
        }

        @WorkerThread
        suspend fun getInt(name: String): Flow<Int?> {//不能使用int, integer 做key
            return dataStore.data
                    /*.catch {
                        if (it is IOException) {
                            it.printStackTrace()
                            emit(emptyPreferences())
                        } else {
                            throw it
                        }
                    }*/.map {
                        it[preferencesKey(name)]
                    }
        }

//        @WorkerThread //调用会运行报错
//        suspend fun getValue(name: String): Flow<Any?> {
//            return dataStore.data
//                    .catch {
//                        if (it is IOException) {
//                            it.printStackTrace()
//                            emit(emptyPreferences())
//                        } else {
//                            throw it
//                        }
//                    }.map {
//                        it[preferencesKey(name)]
//                    }
//        }

//        @WorkerThread //error
//        suspend fun <T: Any> setValue(name: String, value: T) {
//            dataStore.edit {
//                it[preferencesKey<Any>(name)] = value
//            }
//        }

        @WorkerThread
        suspend fun put(name: String, value: Int) {
            dataStore.edit {
                val key = preferencesKey<Int>(name)
//                val key = it[name] ?: 0
                it[key] = value
            }
        }

        @WorkerThread
        suspend fun put(name: String, value: Long) {
            dataStore.edit {
                val key = preferencesKey<Long>(name)
                it[key] = value
            }
        }

        @WorkerThread
        suspend fun put(name: String, value: Float) {
            dataStore.edit {
                val key = preferencesKey<Float>(name)
                it[key] = value
            }
        }

        @WorkerThread
        suspend fun putDouble(name: String, value: Double) {
            dataStore.edit {
                val key = preferencesKey<Double>(name)
                it[key] = value
            }
        }

        @WorkerThread
        suspend fun put(name: String, value: Boolean) {
            dataStore.edit {
                val key = preferencesKey<Boolean>(name)
                it[key] = value
            }
        }

        @WorkerThread
        suspend fun put(name: String, value: String) {
            dataStore.edit {
                val key = preferencesKey<String>(name)
                it[key] = value
            }
        }
    }
}