package com.stone.stoneviewskt.ui.contentp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.stone.stoneviewskt.ui.room.appDatabase
import com.stone.stoneviewskt.util.logi

class MyContentProvider : ContentProvider() {

    /*
     * contentUri = "content://com.stone.cper/"
     * 调用端：contentResolver.insert(CONTENT_URI, contentValues)
     */

    private val matcher by lazy { UriMatcher(UriMatcher.NO_MATCH) }

    companion object {
        const val authority = "com.stone.cper" // 用于 contentUri, 用于 manifest
        const val contentUriStr = "content://$authority"

        private const val FUNC_ADDRESS_ALL = 10
        private const val FUNC_ADDRESS_WHICH = 11
        private const val FUNC_ADDRESS_ADD = 12

        private const val FUNC_USER_DATA_ALL = 20
        private const val FUNC_USER_DATA_WHICH = 21
        private const val FUNC_USER_DATA_ADD = 22
        private const val FUNC_USER_DATA_DEL = 23
        private const val FUNC_USER_DATA_UPDATE = 24
        private const val FUNC_USER_DATA = 25

        private const val TABLE_ADDRESS = "address_data"
        private const val TABLE_USER = "UserData"

    }

    override fun onCreate(): Boolean {
        logi("MyContentProvider onCreate") // 初始化在 application的 attachBaseContext()和 onCreate()之间
        matcher.addURI(authority, "address/all", FUNC_ADDRESS_ALL)
        matcher.addURI(authority, "address/item/add", FUNC_ADDRESS_ADD)
        matcher.addURI(authority, "address/item/#", FUNC_ADDRESS_WHICH) // 这里的#代表任意数字
        matcher.addURI(authority, "address/*", FUNC_ADDRESS_ADD)

        matcher.addURI(authority, "user/all", FUNC_USER_DATA_ALL)
        matcher.addURI(authority, "user/item/#", FUNC_USER_DATA_WHICH) // 这里的#代表任意数字
        matcher.addURI(authority, "user/item/add", FUNC_USER_DATA_ADD)
        matcher.addURI(authority, "user/item/del", FUNC_USER_DATA_DEL)
        matcher.addURI(authority, "user/item/update", FUNC_USER_DATA_UPDATE)
        matcher.addURI(authority, "user/*", FUNC_USER_DATA) // * 则代表匹配任意长度的任意字符
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val table = getTableName(uri)
        return appDatabase.openHelper.readableDatabase.query(
            SupportSQLiteQueryBuilder.builder(table)
                .selection(selection, selectionArgs)
                .columns(projection)
                .orderBy(sortOrder)
                .create()
        )
//        ContentUris.parseId("")
    }

    /*
     * 对于单个记录，返回的MIME类型应该以 vnd.android.cursor.item/ 为首的字符串
     * 对于多个记录，返回 vnd.android.cursor.dir/ 为首的字符串
     *
     * return MIME类型字符串
     */
    override fun getType(uri: Uri): String? {
        val name = when (getTableName(uri)) {
            TABLE_ADDRESS -> "address"
            TABLE_USER -> "user"
            else -> null
        }
        return if (uri.path?.contains("/all") == true) {
            "vnd.android.cursor.dir/$name"
        } else if (uri.path?.contains("/item") == true) {
            "vnd.android.cursor.item/$name"
        } else null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri { // values 中的 key 为表的 列名
        // 插入后，返回的是新 插入的  行id，这和原本数据表的 id 字段没关系；当表有过删除操作后，再插入，那 行id 肯定和 表字段 id 对不上的 ???
        val rowId = appDatabase.openHelper.writableDatabase.insert(getTableName(uri), SQLiteDatabase.CONFLICT_REPLACE, values)
        return ContentUris.withAppendedId(uri, rowId)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return appDatabase.openHelper.writableDatabase.delete(getTableName(uri), selection, selectionArgs)
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return appDatabase.openHelper.writableDatabase.update(getTableName(uri), SQLiteDatabase.CONFLICT_REPLACE, values, selection, selectionArgs)
    }

    private fun getTableName(uri: Uri): String? {
        return when (matcher.match(uri)) {
            FUNC_ADDRESS_ALL -> TABLE_ADDRESS
            FUNC_ADDRESS_ADD -> TABLE_ADDRESS
            FUNC_USER_DATA_WHICH -> TABLE_USER
            FUNC_USER_DATA -> TABLE_USER
            else -> null
        }
    }
}