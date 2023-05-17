package com.stone.stoneviewskt.ui.contentp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.core.net.toUri
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.stone.stoneviewskt.ui.room.appDatabase
import com.stone.stoneviewskt.util.logi

/**
 * desc   : ROOM + ContentProvider 示例
 *          address 相关的增、删、改、查(模糊查)示例 都有了
 *          示例中关于 User表、User相关的Uri 等示例不全
 * author : stone
 * email  : aa86799@163.com
 * time   : 2022/12/13 14:33
 */
class MyContentProvider : ContentProvider() {

    /*
     * contentUri = "content://com.stone.cper/..."
     * 调用端：contentResolver.insert(CONTENT_URI, contentValues)
     */

    private val matcher by lazy { UriMatcher(UriMatcher.NO_MATCH) }

    companion object {
        const val authority = "com.stone.cper" // 用于 contentUri, 用于 manifest
        const val contentUriStr = "content://$authority"

        private const val FUNC_ADDRESS_ALL = 10
        private const val FUNC_ADDRESS_WHICH = 11
        private const val FUNC_ADDRESS_ADD = 12
        private const val FUNC_ADDRESS_DEL = 13
        private const val FUNC_ADDRESS_UPDATE = 14

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
        // matcher.addURI 建立对应关系
        matcher.addURI(authority, "address/all", FUNC_ADDRESS_ALL)
        matcher.addURI(authority, "address/item/add", FUNC_ADDRESS_ADD)
        matcher.addURI(authority, "address/item/del", FUNC_ADDRESS_DEL)
        matcher.addURI(authority, "address/item/update", FUNC_ADDRESS_UPDATE)
        matcher.addURI(authority, "address/item/#", FUNC_ADDRESS_WHICH) // 这里的#代表任意数字，本示例仅在查询使用，查询第几行数据
        matcher.addURI(authority, "address/*", FUNC_ADDRESS_ADD) // * 则代表匹配任意长度的任意字符，一般没啥实际意义

        matcher.addURI(authority, "user/all", FUNC_USER_DATA_ALL)
        matcher.addURI(authority, "user/item/#", FUNC_USER_DATA_WHICH) // 这里的#代表任意数字
        matcher.addURI(authority, "user/item/add", FUNC_USER_DATA_ADD)
        matcher.addURI(authority, "user/item/del", FUNC_USER_DATA_DEL)
        matcher.addURI(authority, "user/item/update", FUNC_USER_DATA_UPDATE)
        matcher.addURI(authority, "user/*", FUNC_USER_DATA) // * 则代表匹配任意长度的任意字符，一般没啥实际意义
        return true
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

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val flag = when (matcher.match(uri)) {
            FUNC_ADDRESS_ALL, FUNC_ADDRESS_WHICH -> true
            FUNC_USER_DATA_ALL, FUNC_USER_DATA_WHICH -> true
            else -> false
        }
        if (!flag) return null // 不符合uri规则就退出；否则 若是一个没有预定义的 uri，后续操作会引发错误

        return getTableName(uri)?.let {
            appDatabase.openHelper.readableDatabase.query(
                SupportSQLiteQueryBuilder.builder(it)
                    .selection(selection, selectionArgs)
                    .columns(projection)
                    .orderBy(sortOrder)
                    .create()
            )
        }
//        ContentUris.parseId(uri) // 可以获取到 uri 路径 最后 的数字，如 content://.../../9  获取到数字 9
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? { // values 中的 key 为表的 列名
        val valuesTemp = values ?: ContentValues()
        val flag = when (matcher.match(uri)) {
            FUNC_ADDRESS_ADD -> true
            FUNC_USER_DATA_ADD -> true
            else -> false
        }
        if (!flag) return null // 不符合uri规则就退出；否则 若是一个没有预定义的 uri，后续操作会引发错误

        val rowId = getTableName(uri)?.let { appDatabase.openHelper.writableDatabase.insert(it, SQLiteDatabase.CONFLICT_REPLACE, valuesTemp) } ?: 0
        // 虽然这样返回，也不会报错；但 uri 是带有 add后缀的； 最终返回的就是 .../add/rowId；不符合返回值的注释语义
        // 查看文档 insert()的文档注释，返回值 Uri，应该是表示 新插入项
        // 感觉应该如上描述的；但最后试了返回 null，也没有什么问题
//        return ContentUris.withAppendedId(uri, rowId)
        return ContentUris.withAppendedId("$contentUriStr/address/item".toUri(), rowId)
//        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val flag = when (matcher.match(uri)) {
            FUNC_ADDRESS_DEL -> true
            FUNC_USER_DATA_DEL -> true
            else -> false
        }
        if (!flag) return 0 // 不符合uri规则就退出；否则 若是一个没有预定义的 uri，后续操作会引发错误
        return getTableName(uri)?.let { appDatabase.openHelper.writableDatabase.delete(it, selection, selectionArgs) } ?: 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val valuesTemp = values ?: ContentValues()
        val flag = when (matcher.match(uri)) {
            FUNC_ADDRESS_UPDATE -> true
            FUNC_USER_DATA_UPDATE -> true
            else -> false
        }
        if (!flag) return 0 // 不符合uri规则就退出；否则 若是一个没有预定义的 uri，后续操作会引发错误
        return getTableName(uri)?.let {appDatabase.openHelper.writableDatabase.update(it, SQLiteDatabase.CONFLICT_REPLACE, valuesTemp, selection, selectionArgs) } ?: 0
    }

    // 根据 uri，匹配出对应的数据表
    private fun getTableName(uri: Uri): String? {
        return when (matcher.match(uri)) {
            FUNC_ADDRESS_ALL, FUNC_ADDRESS_ADD, FUNC_ADDRESS_DEL, FUNC_ADDRESS_WHICH, FUNC_ADDRESS_UPDATE -> TABLE_ADDRESS
            FUNC_USER_DATA_WHICH, FUNC_USER_DATA -> TABLE_USER
            else -> null
        }
    }
}