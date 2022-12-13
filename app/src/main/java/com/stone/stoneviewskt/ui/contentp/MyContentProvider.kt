package com.stone.stoneviewskt.ui.contentp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.stone.stoneviewskt.util.logi

class MyContentProvider: ContentProvider() {

    /*
     * content://com.stone.cper/
     */

    companion object {
        private const val authority = "com.stone.cper"
        private val matcher by lazy { UriMatcher(UriMatcher.NO_MATCH) }
    }

    override fun onCreate(): Boolean {
        logi("MyContentProvider onCreate") // 初始化在 application的 attachBaseContext()和 onCreate()之间
        matcher.addURI(authority, "testA", 1)
        matcher.addURI(authority, "testB", 2)
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        getTableName(uri)
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    private fun getTableName(uri: Uri): String? {
        return when (matcher.match(uri)) {
            1 -> "testA"
            2 -> "testB"
            else -> null
        }
    }
}