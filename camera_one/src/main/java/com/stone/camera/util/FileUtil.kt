package com.stone.camera.util

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.stone.StoneApp
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtil {
    private const val TAG = "FileUtil"
//    private val parentPath = StoneApp.instance.externalCacheDir
    private val parentPath = StoneApp.instance.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    private var storagePath = ""
    private const val DST_FOLDER_NAME = "PlayCamera"

    /**初始化保存路径
     * @return
     */
    private fun initPath(): String {
        if (storagePath == "" && parentPath != null) {
            storagePath = parentPath.absolutePath + "/" + DST_FOLDER_NAME
            val f = File(storagePath)
            if (!f.exists()) {
                f.mkdir()
            }
        }
        return storagePath
    }

    /**保存Bitmap到sdcard
     * @param bm
     */
    @JvmStatic
    fun saveBitmap(bm: Bitmap) {
        val path = initPath()
        val dataTake = System.currentTimeMillis()
        val jpegName = "$path/$dataTake.jpg"
        Log.i(TAG, "saveBitmap:jpegName = $jpegName")
        try {
            val out = FileOutputStream(jpegName)
            val bos = BufferedOutputStream(out)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
            Log.i(TAG, "saveBitmap 成功")
        } catch (e: IOException) {
            Log.i(TAG, "saveBitmap 失败")
            e.printStackTrace()
        }
    }
}